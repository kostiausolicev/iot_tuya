package ru.headh.kosti.telegrambot.enumeration

enum class ActionType {
    START, MAIN_MENU, HOME_MENU, DEVICE_MENU,

    REGISTER, AUTH, PROFILE, SING_OUT, DELETE_USER,

    CREATE_HOME, DELETE_HOME, GET_HOME, WAS_CREATED_HOME, GET_HOME_LIST,

    CREATE_ROOM, DELETE_ROOM, GET_ROOM, WAS_CREATED_ROOM, GET_ROOM_LIST,

    GET_DEVICE_LIST, CREATE_DEVICE, UPDATE_DEVICE, GET_DEVICE, DELETE_DEVICE,
    SET_DEVICE_HOME, SET_DEVICE_ROOM,
    CHANGE_DEVICE_STATE, WAS_CREATED_DEVICE, WAS_CHANGED_DEVICE_STATE
}