package com.human.expert.adapters;

/**
 * Created by Michal365 on 15.04.2014.
 */
public class ScenariosModel {
    public String text;
    public int id;
    public int caseId;

    public ScenariosModel(String _text, int _id, int _caseId){
        this.text = _text;
        this.id = _id;
        this.caseId = _caseId;
    }
}
