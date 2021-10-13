package com.example.chuibbo_android.calendar

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.chuibbo_android.home.JobPost
import com.example.chuibbo_android.home.JobPostDataSource
import java.time.LocalDateTime

class BookMarkDataSource(resources: Resources) {

    val initialBookMarkList = listOf(
        BookMark(
            1,
            1,
            JobPost(
                1,
                "롯데제과1",
                "롯데제과 채용공고1",
                3,
                "http://image.newdaily.co.kr/site/data/img/2020/06/18/2020061800019_0.png",
                "https://www.lotteconf.co.kr/",
                LocalDateTime.of(2021, 9, 30, 12, 0, 0),
                LocalDateTime.of(2021, 10, 20, 12, 0, 0)
            )
        ),
        BookMark(
            2,
            1,
            JobPost(
                2,
                "두산1",
                "두산 채용1",
                2,
                "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f5/Doosan_logo.svg/1200px-Doosan_logo.svg.png",
                "https://www.doosan.com/kr",
                LocalDateTime.of(2021, 8, 30, 12, 0, 0),
                LocalDateTime.of(2021, 9, 10, 12, 0, 0)
            )
        ),
        BookMark(
            3,
            1,
            JobPost(
                3,
                "두산",
                "두산 채용",
                2,
                "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f5/Doosan_logo.svg/1200px-Doosan_logo.svg.png",
                "https://www.doosan.com/kr",
                LocalDateTime.of(2021, 8, 26, 12, 0, 0),
                LocalDateTime.of(2021, 8, 30, 12, 0, 0)
            )
        ),
        BookMark(
            4,
            1,
            JobPost(
                4,
                "금강제과",
                "금강제과 채용공고",
                3,
                "http://image.newdaily.co.kr/site/data/img/2020/06/18/2020061800019_0.png",
                "https://www.lotteconf.co.kr/",
                LocalDateTime.of(2021, 10, 1, 12, 0, 0),
                LocalDateTime.of(2021, 10, 11, 12, 0, 0)
            )
        ),
        BookMark(
            5,
            1,
            JobPost(
                1,
                "롯데제과2",
                "롯데제과 채용공고2",
                3,
                "http://image.newdaily.co.kr/site/data/img/2020/06/18/2020061800019_0.png",
                "https://www.lotteconf.co.kr/",
                LocalDateTime.of(2021, 9, 30, 12, 0, 0),
                LocalDateTime.of(2021, 10, 20, 12, 0, 0)
            )
        ),
        BookMark(
            6,
            1,
            JobPost(
                2,
                "두산2",
                "두산 채용2",
                2,
                "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f5/Doosan_logo.svg/1200px-Doosan_logo.svg.png",
                "https://www.doosan.com/kr",
                LocalDateTime.of(2021, 8, 30, 12, 0, 0),
                LocalDateTime.of(2021, 9, 10, 12, 0, 0)
            )
        )
    )

    private val bookMarkLiveData = MutableLiveData(initialBookMarkList)

    fun addBookMark(bookMark: BookMark) {
        val currentList = bookMarkLiveData.value
        if (currentList == null) {
            bookMarkLiveData.postValue(listOf(bookMark))
        } else {
            val updatedList = currentList.toMutableList()
            updatedList.add(0, bookMark)
            bookMarkLiveData.postValue(updatedList)
        }
    }

    fun getBookMarkForId(id: Long): BookMark? {
        bookMarkLiveData.value?.let { bookMark ->
            return bookMark.firstOrNull{ it.id == id}
        }
        return null
    }

    fun getBookMarkList(): LiveData<List<BookMark>> {
        return bookMarkLiveData
    }

    companion object {
        private var INSTANCE: BookMarkDataSource? = null

        fun getDataSource(resources: Resources): BookMarkDataSource {
            return synchronized(BookMarkDataSource::class) {
                val newInstance = INSTANCE ?: BookMarkDataSource(resources)
                INSTANCE = newInstance
                newInstance
            }
        }
    }
}
