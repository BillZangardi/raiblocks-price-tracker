package me.billzangardi.raiblocks.data.model

/**
 * Created by zangardiw on 12/22/17.
 */

class Data(bToU: Float, bToE: Float, bToG: Float, xToBLast: Float, xToBHigh: Float, xToBLow: Float) {
    val btcToUsd: Float = bToU
    val btcToEur: Float = bToE
    val btcToGbp: Float = bToG
    val xrbToBtcLast: Float = xToBLast
    val xrbToBtcHigh: Float = xToBHigh
    val xrbToBtcLow: Float = xToBLow
}