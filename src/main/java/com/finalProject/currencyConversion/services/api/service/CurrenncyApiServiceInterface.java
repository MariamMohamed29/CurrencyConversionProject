package com.finalProject.currencyConversion.services.api.service;

import com.finalProject.currencyConversion.dto.FavoriteCurrenciesDto;
import com.finalProject.currencyConversion.dto.PairCurrenciesConversionDto;

public interface CurrenncyApiServiceInterface {
    PairCurrenciesConversionDto convertAmount(String base, String target, Double amount);

    FavoriteCurrenciesDto compareCurrencies(String base);
}
