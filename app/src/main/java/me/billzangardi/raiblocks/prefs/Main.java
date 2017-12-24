package me.billzangardi.raiblocks.prefs;


import org.jraf.android.prefs.DefaultFloat;
import org.jraf.android.prefs.Name;
import org.jraf.android.prefs.Prefs;

/**
 * Created by zangardiw on 12/22/17.
 */

@Prefs
public class Main {
    public static final String LAST_UPDATED = "LAST_UPDATED";
    public static final String XRB_TO_BTC = "XRB_TO_BTC";
    public static final String XRB_TO_BTC_HIGH = "XRB_TO_BTC_HIGH";
    public static final String XRB_TO_BTC_LOW = "XRB_TO_BTC_LOW";
    public static final String XRB_TO_USD = "XRB_TO_USD";
    public static final String XRB_TO_EUR = "XRB_TO_EUR";
    public static final String XRB_TO_GBP = "XRB_TO_GBP";
    public static final String XRB_TO_USD_LOW = "XRB_TO_USD_LOW";
    public static final String XRB_TO_EUR_LOW = "XRB_TO_EUR_LOW";
    public static final String XRB_TO_GBP_LOW = "XRB_TO_GBP_LOW";
    public static final String XRB_TO_USD_HIGH = "XRB_TO_USD_HIGH";
    public static final String XRB_TO_EUR_HIGH = "XRB_TO_EUR_HIGH";
    public static final String XRB_TO_GBP_HIGH = "XRB_TO_GBP_HIGH";
    public static final String AMOUNT_OWNED = "AMOUNT_OWNED";

    @Name(LAST_UPDATED)
    String lastUpdated;

    @Name(XRB_TO_BTC)
    @DefaultFloat(0.0000000f)
    Float xrbToBtc;

    @Name(XRB_TO_BTC_HIGH)
    @DefaultFloat(0.0000000f)
    Float xrbToBtcHigh;

    @Name(XRB_TO_BTC_LOW)
    @DefaultFloat(0.0000000f)
    Float xrbToBtcLow;

    @Name(XRB_TO_USD)
    @DefaultFloat(0.00f)
    Float xrbToUsd;

    @Name(XRB_TO_EUR)
    @DefaultFloat(0.00f)
    Float xrbToEur;

    @Name(XRB_TO_GBP)
    @DefaultFloat(0.00f)
    Float xrbToGbp;

    @Name(XRB_TO_USD_HIGH)
    @DefaultFloat(0.00f)
    Float xrbToUsdHigh;

    @Name(XRB_TO_EUR_HIGH)
    @DefaultFloat(0.00f)
    Float xrbToEurHigh;

    @Name(XRB_TO_GBP_HIGH)
    @DefaultFloat(0.00f)
    Float xrbToGbpHigh;

    @Name(XRB_TO_USD_LOW)
    @DefaultFloat(0.00f)
    Float xrbToUsdLow;

    @Name(XRB_TO_EUR_LOW)
    @DefaultFloat(0.00f)
    Float xrbToEurLow;

    @Name(XRB_TO_GBP_LOW)
    @DefaultFloat(0.00f)
    Float xrbToGbpLow;

    @Name(AMOUNT_OWNED)
    @DefaultFloat(0.0000000f)
    Float amountOwned;
}
