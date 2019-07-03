package com.example.medicinehalflife;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "drug_list")
public class Drug {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = DrugClass.DRUG_ID)
    private String mId;

    @ColumnInfo(name = DrugClass.DRUG_NAME)
    public String mName;

    @ColumnInfo(name = DrugClass.DRUG_HALF_LIFE)
    public String mHalfLife;

    public Drug(@NonNull String id, String name, String halfLife){
        this.mId = id;
        this.mName = name;
        this.mHalfLife = halfLife;
    }
    
    public String getId(){
        return mId;
    }

    public String getName(){
        return mName;
    }

    public String getHalfLife(){
        return mHalfLife;
    }

}
