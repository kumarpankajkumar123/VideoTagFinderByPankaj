package com.example.TagFinder.ModalOfApi;

import java.util.List;

public class ResponseModel {

    private String Message;
    private List<Category> Data;

    // Constructor
    public ResponseModel(String message, List<Category> data) {
        this.Message = message;
        this.Data = data;
    }

    // Getters and Setters
    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        this.Message = message;
    }

    public List<Category> getData() {
        return Data;
    }

    public void setData(List<Category> data) {
        this.Data = data;
    }


    public static class Category {
        private String Category;
        private List<SubCategory> SubCategory;

        // Constructor
        public Category(String category, List<SubCategory> subCategory) {
            this.Category = category;
            this.SubCategory = subCategory;
        }

        // Getters and Setters
        public String getCategory() {
            return Category;
        }

        public void setCategory(String category) {
            this.Category = category;
        }

        public List<SubCategory> getSubCategory() {
            return SubCategory;
        }

        public void setSubCategory(List<SubCategory> subCategory) {
            this.SubCategory = subCategory;
        }

        public static class SubCategory {
            private String SubCategory;
            private int Count;

            // Constructor
            public SubCategory(String subCategory, int count) {
                this.SubCategory = subCategory;
                this.Count = count;
            }

            // Getters and Setters
            public String getSubCategory() {
                return SubCategory;
            }

            public void setSubCategory(String subCategory) {
                this.SubCategory = subCategory;
            }

            public int getCount() {
                return Count;
            }

            public void setCount(int count) {
                this.Count = count;
            }
        }
    }
}
