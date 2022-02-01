package com.vet24.web.util;

import com.vet24.service.medicine.DoctorScheduleService;
import com.vet24.service.user.DoctorNonWorkingService;
import com.vet24.service.user.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Component
@EnableScheduling
public class DoctorScheduleBalanceUtil {
    private final DoctorScheduleService doctorScheduleService;
    private final DoctorNonWorkingService doctorNonWorkingService;
    private final DoctorService doctorService;
    private final LocalDate localDate = LocalDate.of(2021, 10, 1);

    //Map(Id доктора, List с неделями)
    private Map<Long, List<Integer>> doctorNonWorkingMap = new HashMap<>();
    //Map <ID доктора, List<Неделя>>
    private Map<Long, List<Map<Integer, String>>> doctorScheduleMap = new HashMap<>();
    //private Map<Long, List<Integer>> doctorScheduleMap = new HashMap<>();
    private int startWeek;
    private int endWeek;
    private int countWorkWeekFirstShift;
    private int countWorkWeekSecondShift;

    @Autowired
    public DoctorScheduleBalanceUtil(DoctorScheduleService doctorScheduleService, DoctorNonWorkingService doctorNonWorkingService, DoctorService doctorService) {
        this.doctorScheduleService = doctorScheduleService;
        this.doctorNonWorkingService = doctorNonWorkingService;
        this.doctorService = doctorService;
    }

    //Проверка доктора, болен ли он на этой неделе
    public boolean doctorNonWorkingByIdAndWeek(Long doctorId, int week) {
        if (doctorNonWorkingMap.containsKey(doctorId) && doctorNonWorkingMap.get(doctorId).contains(week)) {
            return true;
        }
        return false;
    }

    //Проверка есть ли смена у доктора в текущей неделе
    public boolean doctorScheduleExistsByIdAndWeek(Long doctorId, int week) {
        if (doctorScheduleMap.containsKey(doctorId)) {
            List<Map<Integer, String>> list = doctorScheduleMap.get(doctorId);
            for (Map<Integer, String> map : list) {
                if (map.containsKey(week)) {
                    return true;
                }
            }
        }
        return false;
    }

    //Получаем смену у доктора по переданной неделе
    public String getShiftByWeek(Long doctorId, int week) {
        if (doctorScheduleMap.containsKey(doctorId)) {
            List<Map<Integer, String>> list = doctorScheduleMap.get(doctorId);
            for (Map<Integer, String> map : list) {
                String shift = map.get(week);
                return shift == null ? "" : shift;
            }
        }
        return "";
    }

    private void initialize() {
        //LocalDate localDate = LocalDate.now();
        //LocalDate localDate = LocalDate.of(2022, 1, 1);
        LocalDate startDate = localDate;
        LocalDate endDate = LocalDate.of(localDate.getYear(), localDate.getMonth(), localDate.lengthOfMonth());

        loadingDoctorScheduleFromDBToMap();

        int currentDayOfWeek = startDate.getDayOfWeek().getValue();
        System.out.println("День недели - " + currentDayOfWeek);

        //Вычисляем начало недели, с котрой заполняем рабочие смены
        //если текущий день не является первым днем недели, тогда дополняем днями до будущей недели,
        //новая неделя месяца может начинаться не с 1 числа месяца
        if (currentDayOfWeek != 1) {
            startWeek = startDate.plusDays(8 - currentDayOfWeek).get(WeekFields.of(Locale.getDefault()).weekOfYear());
            System.out.println("Текущая неделя - " + startWeek);
        } else {
            //если 1 число месяца совпадает с 1 днем недели
            startWeek = startDate.get(WeekFields.of(Locale.getDefault()).weekOfYear());
        }

        //неделя окончания месяца вычисляется по последнему дню месяца и переходит на будущий месяц
        endWeek = endDate.get(WeekFields.of(Locale.getDefault()).weekOfYear());
        System.out.println("Последняя неделя месяца - " + endWeek);
        int endDayofWeek = endDate.getDayOfWeek().getValue();
        System.out.println("Последний день месяца, день недели " + endDayofWeek);

        //Получаем всех неработающих докторов в будущем месяце, переводим в Map(Id доктора, List с неделями)
        doctorNonWorkingService.getAll()
                .stream()
                .filter(week -> week.getDate().isAfter(startDate.minusDays(1)))
                .forEach(doc -> {
                    if (doctorNonWorkingMap.get(doc.getDoctor().getId()) == null) {
                        List<Integer> tmpList = new ArrayList<>();
                        tmpList.add(doc.getDate().get(WeekFields.of(Locale.getDefault()).weekOfYear()));
                        doctorNonWorkingMap.put(doc.getDoctor().getId(), tmpList);
                    } else {
                        List<Integer> tmpList = doctorNonWorkingMap.get(doc.getDoctor().getId());
                        tmpList.add(doc.getDate().get(WeekFields.of(Locale.getDefault()).weekOfYear()));
                        doctorNonWorkingMap.put(doc.getDoctor().getId(), tmpList);
                    }
                });

        //Удалить после тестов
        System.out.println("Мапа с нерабочими неделями доктора " + doctorNonWorkingMap);
    }

    //Количество докторов работающих в первую, вторую смену на неделе - week
    public void calculateFirstSecondShiftForWeek(int week) {
        countWorkWeekFirstShift = 0;
        countWorkWeekSecondShift = 0;

        for (Map.Entry<Long, List<Map<Integer, String>>> mapEntites : doctorScheduleMap.entrySet()) {
            for (Map<Integer, String> mapWeekShift : mapEntites.getValue()) {
                for (Map.Entry<Integer, String> result : mapWeekShift.entrySet()) {
                    if (result.getKey() == week) {
                        if (result.getValue().equals("FIRST_SHIFT")) {
                            countWorkWeekFirstShift++;
                        } else {
                            countWorkWeekSecondShift++;
                        }
                    }
                }
            }
        }
    }

    //Получение все смен докторов за месяц
    //Создаем Map <ID доктора, List<Map<№ недели, смена>>
    public void loadingDoctorScheduleFromDBToMap() {
        doctorScheduleService.getAll()
                .stream()
                .filter(week -> week.getWeekNumber() >= localDate.minusWeeks(1).get(WeekFields.of(Locale.getDefault()).weekOfYear()))
                .forEach(doc -> {
                    if (doctorScheduleMap.get(doc.getDoctor().getId()) == null) {
                        Map<Integer, String> tmpMap = new HashMap<>();
                        List<Map<Integer, String>> tmpList = new ArrayList<>();
                        tmpMap.put(doc.getWeekNumber(), doc.getWorkShift().toString());
                        tmpList.add(tmpMap);
                        doctorScheduleMap.put(doc.getDoctor().getId(), tmpList);
                    } else {
                        List<Map<Integer, String>> tmpList = doctorScheduleMap.get(doc.getDoctor().getId());
                        Map<Integer, String> tmpMap = new HashMap<>();
                        tmpMap.put(doc.getWeekNumber(), doc.getWorkShift().toString());
                        tmpList.add(tmpMap);
                        doctorScheduleMap.put(doc.getDoctor().getId(), tmpList);
                    }
                });

        //Удалить после тестов
        System.out.println("Мапа с докторами и сменами " + doctorScheduleMap);
    }

    public int getStartWeek() {
        initialize();
        return startWeek;
    }

    public int getEndWeek() {
        return endWeek;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public int getCountWorkWeekFirstShift() {
        return countWorkWeekFirstShift;
    }

    public int getCountWorkWeekSecondShift() {
        return countWorkWeekSecondShift;
    }

    public void setCountWorkWeekFirstShift(int countWorkWeekFirstShift) {
        this.countWorkWeekFirstShift = countWorkWeekFirstShift;
    }

    public void setCountWorkWeekSecondShift(int countWorkWeekSecondShift) {
        this.countWorkWeekSecondShift = countWorkWeekSecondShift;
    }
}
