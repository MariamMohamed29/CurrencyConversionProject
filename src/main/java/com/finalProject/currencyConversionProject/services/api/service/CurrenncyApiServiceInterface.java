package com.finalProject.CurrencyConversionProject.services.api.service;

import com.finalProject.CurrencyConversionProject.dto.FavoriteCurrenciesDto;
import com.finalProject.CurrencyConversionProject.dto.PairCurrenciesConversionDto;

public interface CurrenncyApiServiceInterface {
    PairCurrenciesConversionDto convertAmount(String base, String target, Double amount);

    FavoriteCurrenciesDto compareCurrencies(String base);
}
