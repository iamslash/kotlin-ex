package com.iamslash.excaffine.common

class DataObject(val data: String) {

    companion object {
        private var objectCounter = 0

        operator fun get(data: String): DataObject {
            objectCounter++
            return DataObject(data)
        }
    }
}
