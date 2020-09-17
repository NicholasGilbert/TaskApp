package com.example.myapplication

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.os.IResultReceiver
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.myapplication.data.RoomTask
import com.example.myapplication.helper.TaskAdapter
import com.example.myapplication.data.TaskCard
import com.example.myapplication.helper.DataDatabase
import com.example.myapplication.helper.SwipeToDelete
import kotlinx.android.synthetic.main.activity_main.btnAdd

class MainActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: TaskAdapter
    var taskArray: ArrayList<RoomTask> = ArrayList()
    lateinit var mRelative: RelativeLayout
    lateinit var layoutManager: LinearLayoutManager
    lateinit var popup: PopupWindow
    lateinit var swipe: SwipeToDelete
    lateinit var itemTouchHelper: ItemTouchHelper
    lateinit var db: DataDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mRelative = findViewById(R.id.main_lay)
        recyclerView = findViewById(R.id.recycler_view)

        db = Room.databaseBuilder(applicationContext, DataDatabase::class.java, "data.db").allowMainThreadQueries().build()

        if(db.DataDAO().getData() != null){
            taskArray = db.DataDAO().getData() as ArrayList<RoomTask>
            adapter = TaskAdapter(taskArray)

            swipe = SwipeToDelete(adapter)
            itemTouchHelper = ItemTouchHelper(swipe)
            itemTouchHelper.attachToRecyclerView(recyclerView)

            layoutManager = LinearLayoutManager(this)
            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = adapter
        }

        btnAdd.setOnClickListener {
            val layoutInflater = this.applicationContext.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val customView = layoutInflater.inflate(R.layout.popup,null)
            popup = PopupWindow(customView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            popup.isOutsideTouchable = true
            popup.isFocusable = true
            popup.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            popup.showAtLocation(mRelative, Gravity.CENTER, 0, 0)

            val addingButton = customView.findViewById(R.id.btnAddTask) as Button
            val inTime = customView.findViewById(R.id.etTime) as EditText
            val inName = customView.findViewById(R.id.etName) as EditText
            val inNotes = customView.findViewById(R.id.etNotes) as EditText

            addingButton.setOnClickListener {
                    val taskHolder = (RoomTask(
                        inTime.text.toString(),
                        inName.text.toString(),
                        inNotes.text.toString()
                    ))
                    addData(taskHolder)
                popup.dismiss()
            }
        }
    }

    fun addData(inData: RoomTask){
        val temp: ArrayList<RoomTask> = ArrayList()
        temp.add(inData)
        db.DataDAO().insert(temp)
        taskArray.add(inData)
        adapter = TaskAdapter(taskArray)

        swipe = SwipeToDelete(adapter)
        itemTouchHelper = ItemTouchHelper(swipe)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }

    override fun onPause() {
        super.onPause()
        val holder = adapter.getDataArray()
        if (holder != taskArray){
            taskArray.removeAll(holder)
//            db.clearAllTables()
//            db.DataDAO().insert(holder)
            for(task in taskArray){
                db.DataDAO().delete(task.time)
            }
//            db.DataDAO().delete(holder)
        }
        db.DataDAO().update(taskArray)
    }
}