package com.human.expert.adapters;

import android.graphics.Bitmap;

/**
 * Created by Michal365 on 15.04.2014.
 */
public class CasesModel {
    public String text;
    public Bitmap image;
    public int caseId;

    public CasesModel(String _text, Bitmap _image, int _caseId){
        this.text = _text;
        this.image = _image;
        this.caseId = _caseId;
    }
}
