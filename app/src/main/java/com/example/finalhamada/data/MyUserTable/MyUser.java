package com.example.finalhamada.data.MyUserTable;

public class MyUser {

    public class UserTable {

        // الخصائص (الصفات)
        private int user_id;
        private String username;
        private String email;
        private String password;
        private int age;
        private String gender;
        private float height;
        private float weight;
        private int goalCalories;
        private int dailyIntake;
        private String activityLevel;
        private String profileImage;

        // المُنشئ (Constructor)
        public UserTable(int user_id, String username, String email, String password,
                         int age, String gender, float height, float weight,
                         int goalCalories, int dailyIntake,
                         String activityLevel, String profileImage) {
            this.user_id = user_id;
            this.username = username;
            this.email = email;
            this.password = password;
            this.age = age;
            this.gender = gender;
            this.height = height;
            this.weight = weight;
            this.goalCalories = goalCalories;
            this.dailyIntake = dailyIntake;
            this.activityLevel = activityLevel;
            this.profileImage = profileImage;
        }

        // Getters و Setters
        public int getUserId() {
            return user_id;
        }

        public void setUserId(int user_id) {
            this.user_id = user_id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public float getHeight() {
            return height;
        }

        public void setHeight(float height) {
            this.height = height;
        }

        public float getWeight() {
            return weight;
        }

        public void setWeight(float weight) {
            this.weight = weight;
        }

        public int getGoalCalories() {
            return goalCalories;
        }

        public void setGoalCalories(int goalCalories) {
            this.goalCalories = goalCalories;
        }

        public int getDailyIntake() {
            return dailyIntake;
        }

        public void setDailyIntake(int dailyIntake) {
            this.dailyIntake = dailyIntake;
        }

        public String getActivityLevel() {
            return activityLevel;
        }

        public void setActivityLevel(String activityLevel) {
            this.activityLevel = activityLevel;
        }

        public String getProfileImage() {
            return profileImage;
        }

        public void setProfileImage(String profileImage) {
            this.profileImage = profileImage;
        }
    }

}
