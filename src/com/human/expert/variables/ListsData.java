package com.human.expert.variables;

import com.human.expert.adapters.ScenariosModel;

import java.util.ArrayList;

/**
 * Created by Michal365 on 15.04.2014.
 */
public abstract class ListsData {
    private static ArrayList<ScenariosModel> scenariosList;

    public static ArrayList<ScenariosModel> getScenariosList() {
        return scenariosList;
    }

    public static void setScenariosList(ArrayList<ScenariosModel> scenariosList) {
        ListsData.scenariosList = scenariosList;
    }

    public static void initScenariouList(){
        scenariosList = new ArrayList<ScenariosModel>();
    }
}
