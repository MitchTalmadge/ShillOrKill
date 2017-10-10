package com.mitchtalmadge.shillorkill.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a Ticker on CoinMarketCap.
 */
public class CMCTickerDTO {

    public String id;

    public String name;

    public String symbol;

    public int rank;

    @JsonProperty("price_usd")
    public double priceUSD;

    @JsonProperty("price_btc")
    public double priceBTC;

    @JsonProperty("24h_volume_usd")
    public double oneDayVolumeUSD;

    @JsonProperty("market_cap_usd")
    public double marketCapUSD;

    @JsonProperty("available_supply")
    public double availableSupply;

    @JsonProperty("total_supply")
    public double totalSupply;

    @JsonProperty("percent_change_1h")
    public float percentChangeOneHour;

    @JsonProperty("percent_change_24h")
    public float percentChangeOneDay;

    @JsonProperty("percent_change_7d")
    public float percentChangeOneWeek;

    @JsonProperty("last_updated")
    public long lastUpdated;
}
