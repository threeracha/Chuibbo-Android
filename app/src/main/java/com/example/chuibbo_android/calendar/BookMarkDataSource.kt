package com.example.chuibbo_android.calendar

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.chuibbo_android.home.*
import java.time.LocalDateTime

class BookMarkDataSource(resources: Resources) {

    val initialBookMarkList = listOf(
        BookMark(
            1,
            1,
            JobPost(
                1,
                "http://image.newdaily.co.kr/site/data/img/2020/06/18/2020061800019_0.png",
                "롯데제과",
                "롯데제과 채용공고",
                "https://www.lotteconf.co.kr/",
                "2021-11-27 23:59:59",
                "2021-11-27 23:59:59",
                listOf(Area(1, "")),
                listOf(Job(1, "")),
                listOf(CareerType(1, ""))
            )
        ),
        BookMark(
            2,
            1,
            JobPost(
                1,
                "http://image.newdaily.co.kr/site/data/img/2020/06/18/2020061800019_0.png",
                "롯데제과",
                "롯데제과 채용공고",
                "https://www.lotteconf.co.kr/",
                "2021-11-27 23:59:59",
                "2021-11-27 23:59:59",
                listOf(Area(1, "")),
                listOf(Job(1, "")),
                listOf(CareerType(1, ""))
            )
        ),
        BookMark(
            3,
            1,
            JobPost(
                1,
                "http://image.newdaily.co.kr/site/data/img/2020/06/18/2020061800019_0.png",
                "롯데제과",
                "롯데제과 채용공고",
                "https://www.lotteconf.co.kr/",
                "2021-11-27 23:59:59",
                "2021-11-27 23:59:59",
                listOf(Area(1, "")),
                listOf(Job(1, "")),
                listOf(CareerType(1, ""))
            )
        ),
        BookMark(
            4,
            1,
            JobPost(
                1,
                "http://image.newdaily.co.kr/site/data/img/2020/06/18/2020061800019_0.png",
                "롯데제과",
                "롯데제과 채용공고",
                "https://www.lotteconf.co.kr/",
                "2021-11-27 23:59:59",
                "2021-11-27 23:59:59",
                listOf(Area(1, "")),
                listOf(Job(1, "")),
                listOf(CareerType(1, ""))
            )
        ),
        BookMark(
            5,
            1,
            JobPost(
                1,
                "http://image.newdaily.co.kr/site/data/img/2020/06/18/2020061800019_0.png",
                "롯데제과",
                "롯데제과 채용공고",
                "https://www.lotteconf.co.kr/",
                "2021-11-27 23:59:59",
                "2021-11-27 23:59:59",
                listOf(Area(1, "")),
                listOf(Job(1, "")),
                listOf(CareerType(1, ""))
            )
        ),
        BookMark(
            6,
            1,
            JobPost(
                1,
                "http://image.newdaily.co.kr/site/data/img/2020/06/18/2020061800019_0.png",
                "롯데제과",
                "롯데제과 채용공고",
                "https://www.lotteconf.co.kr/",
                "2021-11-27 23:59:59",
                "2021-11-27 23:59:59",
                listOf(Area(1, "")),
                listOf(Job(1, "")),
                listOf(CareerType(1, ""))
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
