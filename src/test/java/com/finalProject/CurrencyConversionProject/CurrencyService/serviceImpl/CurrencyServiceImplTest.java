package com.finalProject.CurrencyConversionProject.CurrencyService.serviceImpl;

import com.finalProject.CurrencyConversionProject.apiService.CurrenncyApiServiceInterface;
import com.finalProject.CurrencyConversionProject.currencyService.serviceImpl.CurrencyServiceImpl;
import com.finalProject.CurrencyConversionProject.dto.FavoriteCurrenciesDto;
import com.finalProject.CurrencyConversionProject.validation.InputValidation;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CurrencyServiceInterfaceTest {

    @Mock
    private CurrenncyApiServiceInterface currenncyApiService;
    @Mock
    private InputValidation inputValidation;
    @InjectMocks
    private CurrencyServiceImpl currencyService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    void convertAmount() {

    }

    @Test
    void compareCurrencies() {
        String base = "USD";

        List<String> favorites = Arrays.asList("EUR", "EGP");

        Map<String, Double> conversionRates = new HashMap<>();
        conversionRates.put(favorites.get(0), 0.9262);
        conversionRates.put(favorites.get(1), 30.9015);

        FavoriteCurrenciesDto favoriteCurrenciesDto = FavoriteCurrenciesDto.builder()
                .conversion_rates(conversionRates).build();

        when(currenncyApiService.compareCurrencies(base)).thenReturn(favoriteCurrenciesDto);
        //willDoNothing().given(inputValidation).checkCurrency(anyString());
        //willDoNothing().given(inputValidation).checkList(favorites, favorites.size());

        FavoriteCurrenciesDto response = this.currencyService.compareCurrencies(favorites, base);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getConversion_rates()).isEqualTo(favoriteCurrenciesDto.getConversion_rates());
    }

    @Test
    void getCurrencies() {
    }

    @Test
    void compareTwoCurrencies() {
    }
}