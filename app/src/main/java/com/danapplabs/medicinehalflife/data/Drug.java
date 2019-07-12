package com.danapplabs.medicinehalflife.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "drug_list")
public class Drug {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = DrugClass.DRUG_NAME)
    public String mName;

    @ColumnInfo(name = DrugClass.DRUG_HALF_LIFE)
    public String mHalfLife;

    @ColumnInfo(name = DrugClass.DRUG_HALF_LIFE_UNIT)
    public String mHalfLifeUnit;

    public Drug(@NonNull String name, String halfLife, String halfLifeUnit) {
        this.mName = name;
        this.mHalfLife = halfLife;
        this.mHalfLifeUnit = halfLifeUnit;
    }

    public String getName() {
        return mName;
    }

    public String getHalfLife() {
        return mHalfLife;
    }

    public String getHalfLifeUnit(){ return mHalfLifeUnit;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        //return super.equals(obj);
        String comp1 = this.getName();
        String comp2 = ((Drug)obj).getName();
        return comp1.equals(comp2);
    }
}
