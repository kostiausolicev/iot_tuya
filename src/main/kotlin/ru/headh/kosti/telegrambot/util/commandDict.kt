package ru.headh.kosti.telegrambot.util

import ru.headh.kosti.apigateway.client.model.DeviceDtoCapabilitiesInnerGenGen

val commandDict = mapOf(
    DeviceDtoCapabilitiesInnerGenGen.Code.SWITCH_LED to "Включено",
    DeviceDtoCapabilitiesInnerGenGen.Code.TEMPERATURE to "Температура",
    DeviceDtoCapabilitiesInnerGenGen.Code.BRIGHTNESS to "Яркость",
    DeviceDtoCapabilitiesInnerGenGen.Code.COLOR to "Цвет"
)