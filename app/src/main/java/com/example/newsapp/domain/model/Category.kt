package com.example.newsapp.domain.model

enum class Category(val value: String) {
    GENERAL("general"),
    BUSINESS("business"),
    ENTERTAINMENT("entertainment"),
    HEALTH("health"),
    SCIENCE("science"),
    SPORTS("sports"),
    TECHNOLOGY("technology");

    companion object {
        fun fromValue(value: String): Category {
            return values().firstOrNull { it.value == value } ?: GENERAL
        }
    }
}