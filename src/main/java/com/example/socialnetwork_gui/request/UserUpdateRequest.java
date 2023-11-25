package com.example.socialnetwork_gui.request;

import java.util.UUID;

public class UserUpdateRequest {

        private UUID uid;
        private String username;
        private String password;
        private String email;

        public UserUpdateRequest(UUID uid,String username,String email) {
            this.uid = uid;
            this.username = username;
            this.email = email;
        }
        public UUID getUid(){return uid;}

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }

