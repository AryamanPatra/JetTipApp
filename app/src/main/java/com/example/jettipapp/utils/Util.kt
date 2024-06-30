package com.example.jettipapp.utils

fun calculateTotalTip(tipRatio: Float, totalBill: Float): Float {
    return if (totalBill > 1 && totalBill.toString().isNotEmpty()) "%.2f".format(tipRatio)
        .toFloat() * totalBill
    else 0f
}

fun calculateTotalBillPerPerson(totalBill: Float, splitBy: Int, tipRatio: Float): Float {
    return (calculateTotalTip(tipRatio, totalBill) + totalBill) / splitBy
}