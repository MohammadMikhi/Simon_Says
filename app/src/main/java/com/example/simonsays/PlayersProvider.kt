package com.example.simonsays

import android.content.*
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.net.Uri
import android.text.TextUtils
import java.lang.IllegalArgumentException
class PlayersProvider : ContentProvider() {
    companion object {
        private const val PROVIDER_NAME = "com.example.simonsays.PlayersProvider"
        private const val URL = "content://$PROVIDER_NAME/players"
        val CONTENT_URI: Uri = Uri.parse(URL)
        const val _ID = "_id"
        const val NAME = "name"
        const val Score = "score"
        const val players = 1
        const val player_ID = 2
        val uriMatcher: UriMatcher? = null
        const val DATABASE_NAME = "playerInfo"
        const val players_TABLE_NAME = "players"
        const val DATABASE_VERSION = 1
        const val CREATE_DB_TABLE = " CREATE TABLE " + players_TABLE_NAME +
                " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " + " name TEXT NOT NULL, " +
                " score TEXT NOT NULL);"
        private var sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        init
        {

            sUriMatcher.addURI(PROVIDER_NAME, "players", players)
            sUriMatcher.addURI(PROVIDER_NAME, "players/#", player_ID)
        }

    }
    private var db: SQLiteDatabase? = null

    class DatabaseHelper constructor(context: Context?) :
        SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
        override fun onCreate(db: SQLiteDatabase) {
            db.execSQL(CREATE_DB_TABLE)
        }
        override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
            TODO("Not yet implemented")
        }

    }

    override fun onCreate(): Boolean {
        val context = context
        val dbHelper = DatabaseHelper(context)
        /**
         * Create a write able database which will trigger its
         * creation if it doesn't already exist.  */
        db = dbHelper.writableDatabase
        return db != null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri {
        /**
         * Add a new student record
         */
        val rowID = db!!.insert(players_TABLE_NAME, "", values)
        /**
         * If record is added successfully
         */
        if (rowID > 0) {
            val _uri = ContentUris.withAppendedId(CONTENT_URI, rowID)
            context!!.contentResolver.notifyChange(_uri, null)
            return _uri
        }
        throw SQLException("Failed to add a record into $uri")
    }

    override fun query(
        uri: Uri, projection: Array<String>?,
        selection: String?, selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        var sortOrder = sortOrder
        val qb = SQLiteQueryBuilder()
        qb.tables = players_TABLE_NAME
        if (uriMatcher != null) {
            when (uriMatcher.match(uri)) {
                /* players -> qb.projectionMap =
                    players_PROJECTION_MAP */
                player_ID -> qb.appendWhere(_ID + "=" + uri.pathSegments[1])
            }
        }


        if (sortOrder == null || sortOrder === "") {
            /**
             * By default sort on student names
             */
            sortOrder = NAME
        }
        val c = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder)
        /**
         * register to watch a content URI for changes  */
        c.setNotificationUri(context!!.contentResolver, uri)
        return c
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        var count = 0
        when (uriMatcher!!.match(uri)) {
            players -> count = db!!.delete(
                players_TABLE_NAME, selection,
                selectionArgs
            )
            player_ID -> {
                val id = uri.pathSegments[1]
                count = db!!.delete(
                    players_TABLE_NAME,
                    _ID + " = " + id +
                            if (!TextUtils.isEmpty(selection)) " AND ($selection)" else "",
                    selectionArgs
                )
            }
            else -> throw IllegalArgumentException("Unknown URI $uri")
        }
        context!!.contentResolver.notifyChange(uri, null)
        return count
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        var count = 0
        count = when (uriMatcher!!.match(uri)) {
            players -> db!!.update(
                players_TABLE_NAME, values, selection,
                selectionArgs
            )
            player_ID -> db!!.update(
                players_TABLE_NAME,
                values,
                NAME + " = " + uri.pathSegments[0] + (if (!TextUtils.isEmpty(selection)) " AND ($selection)" else ""),
                selectionArgs
            )
            else -> throw IllegalArgumentException("Unknown URI $uri")
        }
        context!!.contentResolver.notifyChange(uri, null)
        return count
    }

    override fun getType(uri: Uri): String {
        return when (uriMatcher!!.match(uri)) {
            players -> "vnd.android.cursor.dir/vnd.example.players"
            player_ID -> "vnd.android.cursor.item/vnd.example.players"
            else -> throw IllegalArgumentException("Unsupported URI: $uri")
        }
    }
}