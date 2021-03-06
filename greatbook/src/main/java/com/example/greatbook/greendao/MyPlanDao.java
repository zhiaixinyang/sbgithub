package com.example.greatbook.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.example.greatbook.greendao.entity.MyPlan;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "MY_PLAN".
*/
public class MyPlanDao extends AbstractDao<MyPlan, Long> {

    public static final String TABLENAME = "MY_PLAN";

    /**
     * Properties of entity MyPlan.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "proid");
        public final static Property BgColor = new Property(1, int.class, "bgColor", false, "BG_COLOR");
        public final static Property Content = new Property(2, String.class, "content", false, "CONTENT");
        public final static Property TextColor = new Property(3, int.class, "textColor", false, "TEXT_COLOR");
        public final static Property DetailColor = new Property(4, int.class, "detailColor", false, "DETAIL_COLOR");
        public final static Property TextSize = new Property(5, int.class, "textSize", false, "TEXT_SIZE");
    };


    public MyPlanDao(DaoConfig config) {
        super(config);
    }
    
    public MyPlanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"MY_PLAN\" (" + //
                "\"proid\" INTEGER PRIMARY KEY ," + // 0: id
                "\"BG_COLOR\" INTEGER NOT NULL ," + // 1: bgColor
                "\"CONTENT\" TEXT," + // 2: content
                "\"TEXT_COLOR\" INTEGER NOT NULL ," + // 3: textColor
                "\"DETAIL_COLOR\" INTEGER NOT NULL ," + // 4: detailColor
                "\"TEXT_SIZE\" INTEGER NOT NULL );"); // 5: textSize
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"MY_PLAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, MyPlan entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getBgColor());
 
        String content = entity.getContent();
        if (content != null) {
            stmt.bindString(3, content);
        }
        stmt.bindLong(4, entity.getTextColor());
        stmt.bindLong(5, entity.getDetailColor());
        stmt.bindLong(6, entity.getTextSize());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, MyPlan entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getBgColor());
 
        String content = entity.getContent();
        if (content != null) {
            stmt.bindString(3, content);
        }
        stmt.bindLong(4, entity.getTextColor());
        stmt.bindLong(5, entity.getDetailColor());
        stmt.bindLong(6, entity.getTextSize());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public MyPlan readEntity(Cursor cursor, int offset) {
        MyPlan entity = new MyPlan( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getInt(offset + 1), // bgColor
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // content
            cursor.getInt(offset + 3), // textColor
            cursor.getInt(offset + 4), // detailColor
            cursor.getInt(offset + 5) // textSize
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, MyPlan entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setBgColor(cursor.getInt(offset + 1));
        entity.setContent(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setTextColor(cursor.getInt(offset + 3));
        entity.setDetailColor(cursor.getInt(offset + 4));
        entity.setTextSize(cursor.getInt(offset + 5));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(MyPlan entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(MyPlan entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
