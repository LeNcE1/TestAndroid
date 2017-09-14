package uk.co.ribot.androidboilerplate.data.model;



        public class CreateUser {

            private String status;
            private String error;

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getError() {
                return error;
            }

            public void setError(String error) {
                this.error = error;
            }

            //    @SerializedName("user")
//    @Expose
//    private User user;
//
//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }
//
//    @Override
//    public String toString() {
//        return "CreateUser{" +
//                "user=" + user +
//                '}';
//    }
}
