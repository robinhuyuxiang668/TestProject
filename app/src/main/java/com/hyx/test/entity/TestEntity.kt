package com.hyx.test.entity

class TestEntity(var time: Long, var content: String) {

    override fun toString(): String {
        return "TestEntity{" +
                "time=" + time.toString() +
                ", content='" + content + '\'' +
                '}'
    }

}