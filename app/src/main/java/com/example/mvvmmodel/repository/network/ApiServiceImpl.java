package com.example.mvvmmodel.repository.network;

public class ApiServiceImpl {
    private ApiServiceImpl() {
    }

    public static ApiService getInstance() {
        return CreateApiService.apiService;
    }

    /**
     * Retrofit生成接口对象.
     */
    private static class CreateApiService {
        /**
         * 生成实例
         */
        private static final ApiService apiService = RetrofitServiceManager.getInstance().create(ApiService.class);
    }
}
