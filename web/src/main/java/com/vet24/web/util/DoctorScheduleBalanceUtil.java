package com.vet24.web.util;

import com.vet24.models.enums.WorkShift;
import com.vet24.models.medicine.DoctorSchedule;
import com.vet24.models.user.Doctor;
import com.vet24.service.medicine.DoctorScheduleService;
import com.vet24.service.user.DoctorNonWorkingService;
import com.vet24.service.user.DoctorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Slf4j
public class DoctorScheduleBalanceUtil {
    private final DoctorScheduleService doctorScheduleService;
    private final DoctorNonWorkingService doctorNonWorkingService;
    private final DoctorService doctorService;

    private final LocalDate localDate = LocalDate.now();
    private List<DoctorSchedule> doctorScheduleList;
    private Map<Long, List<Integer>> doctorNonWorkingMap;
    private Map<Long, Set<Map<Integer, String>>> doctorScheduleMap;
    private int startWeek;
    private int endWeek;
    private int countWorkWeekFirstShift;
    private int countWorkWeekSecondShift;

    @Autowired
    public DoctorScheduleBalanceUtil(DoctorScheduleService doctorScheduleService,
                                     DoctorNonWorkingService doctorNonWorkingService,
                                     DoctorService doctorService) {
        this.doctorScheduleService = doctorScheduleService;
        this.doctorNonWorkingService = doctorNonWorkingService;
        this.doctorService = doctorService;
    }

    /**
     * Проверка доктора, болен ли он на этой неделе
     */
    private boolean doctorNonWorkingByIdAndWeek(Long doctorId, int week) {
        if (doctorNonWorkingMap.containsKey(doctorId) && doctorNonWorkingMap.get(doctorId).contains(week)) {
            return true;
        }
        return false;
    }

    /**
     * Проверка есть ли смена у доктора в текущей неделе
     */
    private boolean doctorScheduleExistsByIdAndWeek(Long doctorId, int week) {
        if (doctorScheduleMap.containsKey(doctorId)) {
            Set<Map<Integer, String>> list = doctorScheduleMap.get(doctorId);
            for (Map<Integer, String> map : list) {
                if (map.containsKey(week)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Получаем смену у доктора(doctroId) по переданной неделе(week)
     */
    private String getShiftByWeek(Long doctorId, int week) {
        if (doctorScheduleMap.containsKey(doctorId)) {
            Set<Map<Integer, String>> list = doctorScheduleMap.get(doctorId);
            String shift;
            for (Map<Integer, String> map : list) {
                if (map.containsKey(week)) {
                    shift = map.get(week);
                    return shift;
                }
            }
        }
        return "";
    }

    private void initialize() {
        doctorNonWorkingMap = new HashMap<>();
        doctorScheduleMap = new HashMap<>();
        LocalDate startDate = localDate;
        LocalDate endDate = LocalDate.of(localDate.getYear(), localDate.getMonth(), localDate.lengthOfMonth());

        int currentDayOfWeek = startDate.getDayOfWeek().getValue();

        //Вычисляем начало недели, с которой заполняем рабочие смены
        //если первый день месяца не является первым днем недели, тогда дополняем днями до будущей недели,
        //конечная неделя расчитывается с переходом на новый месяц
        //новая неделя месяца может начинаться не с 1 числа месяца
        if (currentDayOfWeek != 1) {
            startWeek = startDate.plusDays(8 - currentDayOfWeek).get(WeekFields.of(Locale.getDefault()).weekOfYear());
        } else {
            //если 1 число месяца совпадает с 1 днем недели
            startWeek = startDate.get(WeekFields.of(Locale.getDefault()).weekOfYear());
        }

        //неделя окончания месяца вычисляется по последнему дню месяца и переходит на будущий месяц
        endWeek = endDate.get(WeekFields.of(Locale.getDefault()).weekOfYear());

        //Получаем всех неработающих докторов в будущем месяце, переводим в Map(Id доктора, List с неделями)
        doctorNonWorkingService.getDoctorNonWorkingAfterDate(startDate)
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

        //Получение все смен докторов за месяц
        //Заполняем doctorScheduleMap -  Map <ID доктора, List<Map<№ недели, смена>>
        //doctorScheduleService.getAll()
        doctorScheduleService.getDoctorScheduleListAfterWeekNumber(startDate.minusWeeks(2).get(WeekFields.of(Locale.getDefault()).weekOfYear()))
                .stream()
                .forEach(doc -> addDoctorScheduleToMap(doc.getDoctor().getId(), doc.getWorkShift().toString(), doc.getWeekNumber()));

        //Решение проблемы с переходом на новый месяц, если первая неделя - 2 = 52, если 5 число месяца
        // данные которые заполнены в БД за январь не вычитываются, т.к. в условии выборки из БД неделя > 52, 1 не попадает в условие выборки
        if (startWeek == 1 | startWeek == 2) {
            doctorScheduleService.getDoctorScheduleListAfterWeekNumber(0)
                    .stream()
                    .forEach(doc -> addDoctorScheduleToMap(doc.getDoctor().getId(), doc.getWorkShift().toString(), doc.getWeekNumber()));
        }
    }



    /**
     * Количество докторов работающих в первую, вторую смену на неделе - week
     */
    private void calculateFirstSecondShiftForWeek(int week) {
        countWorkWeekFirstShift = 0;
        countWorkWeekSecondShift = 0;

        for (Map.Entry<Long, Set<Map<Integer, String>>> mapEntites : doctorScheduleMap.entrySet()) {
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

    //Основной метод для балансировки смен
    @Scheduled(cron = "0 0 3 * * *")
    public void getBalancer() {
        //Получение всех докторов
        List<Doctor> doctorsList = doctorService.getAll();
        //Выходной лист DoctorSchedule для записи в БД
        doctorScheduleList = new ArrayList<>();
        initialize();
        //Лист для докторов, вышедших с выходных, для балансировки
        List<Doctor> doctorsListNonWorkingMinusOneWeek = new ArrayList<>(0);

        //Начало итераций цикла по неделям текущего месяца
        for (int currentWeek = startWeek; currentWeek <= endWeek; currentWeek++) {
            countWorkWeekFirstShift = 0;
            countWorkWeekSecondShift = 0;

            //Загружаем докторов работающих на текущей неделе, за минусом неработающих
            int finalCurrentWeek = currentWeek;
            List<Doctor> doctorsCurrentWeekList = doctorsList.stream()
                    .filter(doc -> !doctorNonWorkingByIdAndWeek(doc.getId(), finalCurrentWeek))
                    .collect(Collectors.toList());
            //Перемешиваем коллекцию, для случайного распределения смен, в принципе нужно только при начальной инициализации БД
            Collections.shuffle(doctorsCurrentWeekList);

            //Проверка если первое число месяца, заполняем базу полностью на месяц, исключая неработающих
            if (localDate.getDayOfMonth() == 1 | doctorScheduleMap.isEmpty()) {
                for (int i = 0; i < doctorsCurrentWeekList.size(); i++) {

                    //Если у доктора есть уже смена на текущей неделе, пропускаем цикл
                    if (doctorScheduleExistsByIdAndWeek(doctorsCurrentWeekList.get(i).getId(), currentWeek)) {
                        continue;
                    }

                    if (getShiftByWeek(doctorsCurrentWeekList.get(i).getId(), getWeekMinusNWeek(currentWeek - 1)).equals("SECOND_SHIFT")) {
                        doctorScheduleList.add(new DoctorSchedule(doctorsCurrentWeekList.get(i), WorkShift.FIRST_SHIFT, currentWeek));
                        addDoctorScheduleToMap(doctorsCurrentWeekList.get(i).getId(), "FIRST_SHIFT", currentWeek);
                        countWorkWeekFirstShift++;
                    } else if (getShiftByWeek(doctorsCurrentWeekList.get(i).getId(), getWeekMinusNWeek(currentWeek - 1)).equals("FIRST_SHIFT")) {
                        doctorScheduleList.add(new DoctorSchedule(doctorsCurrentWeekList.get(i), WorkShift.SECOND_SHIFT, currentWeek));
                        addDoctorScheduleToMap(doctorsCurrentWeekList.get(i).getId(), "SECOND_SHIFT", currentWeek);
                        countWorkWeekSecondShift++;
                    } else {
                        doctorsListNonWorkingMinusOneWeek.add(doctorsCurrentWeekList.get(i));
                    }
                }
                //Если не первое число и БД не пуста, значит база у нас заполнена, проверяем на вновь прибывших докторов, в зависимости
                //от того где докторов меньше в смене, добавляем ему эту смену
            } else {
                calculateFirstSecondShiftForWeek(currentWeek);
                for (int i = 0; i < doctorsCurrentWeekList.size(); i++) {
                    //Если у доктора есть уже смена на текущей неделе, пропускаем цикл
                    if (doctorScheduleExistsByIdAndWeek(doctorsCurrentWeekList.get(i).getId(), currentWeek)) {
                        continue;
                    }

                    if (!doctorScheduleExistsByIdAndWeek(doctorsCurrentWeekList.get(i).getId(), currentWeek)) {
                        addNewAndNonWorkingDoctor(doctorsCurrentWeekList.get(i), currentWeek);
                    }
                }
            }

            if (!doctorsListNonWorkingMinusOneWeek.isEmpty()) {
                for (int i = 0; i < doctorsListNonWorkingMinusOneWeek.size(); i++) {
                    if (doctorScheduleExistsByIdAndWeek(doctorsCurrentWeekList.get(i).getId(), currentWeek)) {
                        continue;
                    }

                    addNewAndNonWorkingDoctor(doctorsListNonWorkingMinusOneWeek.get(i), currentWeek);
                }
            }
            doctorsListNonWorkingMinusOneWeek.clear();
        }

        doctorsList.clear();
        doctorsListNonWorkingMinusOneWeek.clear();
        doctorScheduleMap.clear();
        doctorNonWorkingMap.clear();

        doctorScheduleService.persistAll(doctorScheduleList);
    }

    /**
     * Дублируем добавление доктора в Map  одновременно с doctorScheduleList с текущей неделей,
     * для проверки чередования смен на будущей неделе
     */
    private void addDoctorScheduleToMap(Long doctorId, String workShift, int weekNumber) {
        if (doctorScheduleMap.get(doctorId) == null) {
            Map<Integer, String> tmpMap = new HashMap<>();
            Set<Map<Integer, String>> tmpList = new HashSet<>();
            tmpMap.put(weekNumber, workShift);
            tmpList.add(tmpMap);
            doctorScheduleMap.put(doctorId, tmpList);
        } else {
            Set<Map<Integer, String>> tmpList = doctorScheduleMap.get(doctorId);
            Map<Integer, String> tmpMap = new HashMap<>();
            tmpMap.put(weekNumber, workShift);
            tmpList.add(tmpMap);
            doctorScheduleMap.put(doctorId, tmpList);
        }
    }

    /**
     * Балансировка смен за счет вновь принятых докторов, или вышедших с нерабочих дней
     */
    private void addNewAndNonWorkingDoctor(Doctor doctor, int week) {
        if ((countWorkWeekFirstShift < countWorkWeekSecondShift)
                && (!getShiftByWeek(doctor.getId(), getWeekMinusNWeek(week - 1)).equals("FIRST_SHIFT")
                && !getShiftByWeek(doctor.getId(), getWeekMinusNWeek(week - 2)).equals("FIRST_SHIFT"))) {
            doctorScheduleList.add(new DoctorSchedule(doctor, WorkShift.FIRST_SHIFT, week));
            addDoctorScheduleToMap(doctor.getId(), "FIRST_SHIFT", week);
            countWorkWeekFirstShift++;
        } else {
            doctorScheduleList.add(new DoctorSchedule(doctor, WorkShift.SECOND_SHIFT, week));
            addDoctorScheduleToMap(doctor.getId(), "SECOND_SHIFT", week);
            countWorkWeekSecondShift++;
        }
    }

    /**
     * Метод для подсчета недель при переходе на новый год
     */
    private int getWeekMinusNWeek(int minusWeek) {
        if (minusWeek == 0) {
            return 52;
        } else if (minusWeek == -1) {
            return 51;
        } else if (minusWeek == -2) {
            return 50;
        } else if (minusWeek == -3) {
            return 49;
        } else return minusWeek;
    }
}