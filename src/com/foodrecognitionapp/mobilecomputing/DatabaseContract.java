package com.foodrecognitionapp.mobilecomputing;

import android.provider.BaseColumns;

public final class DatabaseContract
{
	public DatabaseContract(){}

    /* Inner class that defines the table contents */
    public static abstract class ColorDB implements BaseColumns {
        public static final String TABLE_NAME = "FoodGroupColors";
        public static final String COLUMN_NAME_FOOD_GROUP = "FoodGroup";
        public static final String COLUMN_COLOR = "Color";
        public static final String COLUMN_NUTRIENT = "Nutrient";
        public static final String COLUMN_DESCRIPTION = "Description";
		public static final String COLUMN_NAME_NULLABLE = null;
	}
    
}

