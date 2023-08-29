package com.finalProject.currencyConversionProject.services.api.service;

import com.finalProject.currencyConversionProject.dto.FavoriteCurrenciesDto;
import com.finalProject.currencyConversionProject.dto.PairCurrenciesConversionDto;

public interface CurrenncyApiServiceInterface {
    PairCurrenciesConversionDto convertAmount(String base, String target, Double amount);

    FavoriteCurrenciesDto compareCurrencies(String base);
}
