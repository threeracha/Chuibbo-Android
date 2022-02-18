package com.example.chuibbo_android.home

class JobPostSearchFilter {

    enum class CareerType (
        val koName: String
    ) {
        CAREER_TYPE_TITLE("경력여부"),
        NEW("신입"),
        CAREER("경력"),
    }

    enum class JobFieldType (
        val koName: String
    ) {
        JOB_FIELD_TYPE_TITLE("직업분야"),
        MARKETING("기획/마케팅"),
        DESIGN("디자인"),
        IT("IT"),
    }

    enum class Area (
        val koName: String
    ) {
        AREA_TITLE("위치"),
        SEOUL("서울"),
        GYEONGGI("경기"),
        INCHEON("인천"),
        GANGWON("강원"),
        CHUNGCHEONG("충청"),
        JEOLLA("전라"),
        GYEONGSANG("경상"),
        JEJU("제주"),
    }

    enum class SortType (
        val koName: String
    ) {
        SORT_TYPE_TITLE("정렬"),
        LATEST("최신순"),
        CLOSING("마감순")
    }
}






