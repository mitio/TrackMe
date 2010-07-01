package com.drone.trackme.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a class that is used to save and retrieve data into database.  
 *
 * @author martin.mitev
 */
public class DBHelper
{
	//public static final String DEVICE_ALERT_ENABLED_ZIP = "DAEZ99";
	/**
	 * Represents name of database where coordinates are saved in off-line mode.  
	 *
	 * @author martin.mitev
	 */
	public static final String DB_NAME = "TrackMe";
	/**
	 * Represents table name where coordinates are saved in off-line mode.  
	 *
	 * @author martin.mitev
	 */
	public static final String DB_TABLE = "Coordinates";
	/**
	 * Represents version of database.  
	 *
	 * @author martin.mitev
	 */
	public static final int DB_VERSION = 1;

	//private static final String CLASSNAME = DBHelper.class.getSimpleName();
	private String[] COLS = new String[] { "_id", "trackID", "latitude",
			"longitude", "datetime" };

	private SQLiteDatabase db;
	private final DBOpenHelper dbOpenHelper;

	private static class DBOpenHelper extends SQLiteOpenHelper
	{
		private static final String DB_CREATE = "CREATE TABLE "
				+ DBHelper.DB_TABLE
				+ " (_id INTEGER PRIMARY KEY, trackID INTEGER ,latitude REAL, longitude REAL, datetime TEXT);";

		public DBOpenHelper(final Context context)
		{
			super(context, DBHelper.DB_NAME, null, DBHelper.DB_VERSION);
		}

		@Override
		public void onCreate(final SQLiteDatabase db)
		{
			try
			{
				db.execSQL(DBOpenHelper.DB_CREATE);
			}			
			catch (SQLException e)
			{
				// Log.e(Constants.LOGTAG, DBHelper.CLASSNAME, e);
			}
		}

		@Override
		/**
		 * Opens database.  
		 *
		 * @author martin.mitev 
		 * @param SQLiteDatabase database to be opened
		 * @see SQLiteDatabase
		 */
		public void onOpen(final SQLiteDatabase db)
		{
			super.onOpen(db);
		}

		@Override
		/**
		 * Updates database.  
		 *
		 * @author martin.mitev 
		 * @param SQLiteDatabase database to be opened
		 * @see SQLiteDatabase
		 */
		public void onUpgrade(final SQLiteDatabase db, final int oldVersion,
				final int newVersion)
		{
			db.execSQL("DROP TABLE IF EXISTS " + DBHelper.DB_TABLE);
			onCreate(db);
		}
	}

	/**
	 * Class constructor with context parameter.  
	 *
	 * @author martin.mitev 
	 * @param Context context
	 * @see Context
	 */
	public DBHelper(final Context context)
	{
		this.dbOpenHelper = new DBOpenHelper(context);
		establishDb();
		
		if(recordCheck() < 1)
		{
			insertInitialData();
		}		
	}

	private void establishDb()
	{
		if (this.db == null)
		{
			this.db = this.dbOpenHelper.getWritableDatabase();
		}
	}

	/**
	 * Clean-up function.  
	 *
	 * @author martin.mitev 
	 */
	public void cleanup()
	{
		if (this.db != null)
		{
			this.db.close();
			this.db = null;
		}
	}

	/**
	 * Gets record count.
	 * Returns count of record in database table.  
	 *
	 * @author martin.mitev 
	 */
	public int recordCheck() 
	{
		/*
	     int recordCount = 0;
	     Cursor countCursor;
	     // count the records currently in the table
	     countCursor = this.db.rawQuery("select count(*) from " + DBHelper.DB_TABLE, null);
	     recordCount = countCursor.getCount();	
	     */
		int recordCount = 0;	
		Cursor c = null;
		try
		{
			c = this.db.query(DBHelper.DB_TABLE, new String[]{"latitude"}, null, null,
					null, null, null);
			recordCount = c.getCount();
		}
		catch (SQLException e)
		{
			// Log.v(Constants.LOGTAG, DBHelper.CLASSNAME, e);
		} 
		finally
		{
			if (c != null && !c.isClosed())
			{
				c.close();
			}
		}
	    return recordCount;
	}
	
	/**
	 * Insert new coordinates into database.  
	 *
	 * @author martin.mitev 
	 * @param TrackedCoordinates coordinates to be tracked
	 * @see TrackedCoordinates
	 */
	public void insert(final TrackedCoordinates trackedCoordinates)
	{
		ContentValues values = new ContentValues();
		values.put("trackID", trackedCoordinates.getTrackID());
		values.put("latitude", trackedCoordinates.getLatitude());
		values.put("longitude", trackedCoordinates.getLongitude());
		values.put("datetime", trackedCoordinates.getDatetime().toString());
		this.db.insert(DBHelper.DB_TABLE, null, values);
	}

	/*
	public void update(final TrackedCoordinates myGeoPoint)
	{
		ContentValues values = new ContentValues();
		values.put("latitude", myGeoPoint.latitude);
		values.put("longitude", myGeoPoint.longitude);
		//values.put("isVisited", geoPoint.date);
		this.db.update(DBHelper.DB_TABLE, values, "_id=" + myGeoPoint.id, null);
	}
	*/

	/*
	public void delete(final long id)
	{
		this.db.delete(DBHelper.DB_TABLE, "_id=" + id, null);
	}

	public void delete(final String name)
	{
		this.db.delete(DBHelper.DB_TABLE, "name='" + name + "'", null);
	}
	*/
	
	/**
	 * Deletes all records in database table.  
	 *
	 * @author martin.mitev 
	 */
	public void deleteAll()
	{
		this.db.delete(DBHelper.DB_TABLE, null, null);
	}

	/*
	public GeoPoint get(final String name)
	{
		Cursor c = null;
		Place place = null;
		try
		{
			c = this.db.query(true, DBHelper.DB_TABLE, DBHelper.COLS,
					"name = '" + name + "'", null, null, null, null, null);
			if (c.getCount() > 0)
			{
				c.moveToFirst();
				place = new Place();
				place.id = c.getLong(0);
				place.name = c.getString(1);
				place.oblast = c.getString(2);
				place.worktime = c.getString(3);
				place.info = c.getString(4);
				place.latitude = c.getDouble(5);
				place.longitude = c.getDouble(6);
				place.isVisited = c.getInt(7);
			}
		}
		catch (SQLException e)
		{
			// Log.v(Constants.LOGTAG, DBHelper.CLASSNAME, e);
		} finally
		{
			if (c != null && !c.isClosed())
			{
				c.close();
			}
		}
		return place;
	}
	*/

	/**
	 * Gets all record in database table.  
	 *
	 * @author martin.mitev
	 * @see TrackedCoordinates
	 */
	public List<TrackedCoordinates> selectAll()
	{
		ArrayList<TrackedCoordinates> ret = new ArrayList<TrackedCoordinates>();
		Cursor c = null;
		try
		{
			c = this.db.query(DBHelper.DB_TABLE, this.COLS, null, null,
					null, null, null);
			int numRows = c.getCount();
			c.moveToFirst();
			for (int i = 0; i < numRows; ++i)
			{
				TrackedCoordinates trackCoordinates = new TrackedCoordinates();
				trackCoordinates.setTrackID(c.getInt(1));
				trackCoordinates.setLatitude(c.getDouble(2));
				trackCoordinates.setLongitude(c.getDouble(3));
				trackCoordinates.setDatetime(c.getString(4));
		
				ret.add(trackCoordinates);
				c.moveToNext();
			}
		}
		catch (SQLException e)
		{
			// Log.v(Constants.LOGTAG, DBHelper.CLASSNAME, e);
		} 
		finally
		{
			if (c != null && !c.isClosed())
			{
				c.close();
			}
		}
		return ret;
	}
	
	/**
	 * Inserts initial data into database.  
	 *
	 * @author martin.mitev
	 */
	public void insertInitialData()
	{		
		/*
		ContentValues values = new ContentValues();
		values.put("latitude", 42.648483);
		values.put("longitude", 23.342008);
		this.db.insert(DBHelper.DB_TABLE, null, values);
		
		values.put("latitude", 42.647204);
		values.put("longitude", 23.340399);
		this.db.insert(DBHelper.DB_TABLE, null, values);
		
		values.put("latitude", 42.652705);
		values.put("longitude", 23.348435);
		this.db.insert(DBHelper.DB_TABLE, null, values);	
		*/	
	}	
} 