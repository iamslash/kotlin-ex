package com.iamslash.exjackson.jsontypeinfo

import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "className")
//@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
abstract class Shape {
}
