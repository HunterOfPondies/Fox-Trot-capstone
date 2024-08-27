<template>
  <header>
    <div id="homeLink"> Community
      <router-link v-bind:to="{ name: 'home' }"><img
          src="https://res.cloudinary.com/daprq6s7w/image/upload/v1723474352/foxtrot_logo_fin_noText_zwlfxu.svg" /></router-link>
    </div>


    <!-- <router-link id="LoginLink" v-bind:to="{ name: 'login' }">Login |</router-link> -->
    <!-- <router-link id="LoginLink" v-bind:to="{ name: 'logout' }"> Logout</router-link> -->


    <div id="favLog">
      <p v-if="this.$store.state.user.userId != 0" v-bind:to="{ name: 'logout' }" class="auth-link"
        data-toggle="modal" data-target="#logoutModal">
        Logout
      </p>
      <router-link v-else v-bind:to="{ name: 'login' }" class="auth-link">
        Login
      </router-link>
      
    </div>


    <!-- logout modal -->
    <div class="modal fade" id="logoutModal" tabindex="-1" role="dialog" aria-labelledby="logoutModalLabel"
      aria-hidden="true">
      <div class="modal-dialog" role="document">
        <div id="logoutModalContent" class="modal-content">
          <div id="logoutHeader" class="modal-header">
            <h5 class="modal-title" id="logoutModalLabel">Are you sure you want to log out?</h5>

          </div>
          <div id="logoutModalBody" class="modal-body">
            <span>
              <button v-on:click="logout" data-dismiss="modal" id="buttonsOfLogout" type="button"
                class="btn btn-primary">Log out</button>
            </span>
            <span>
              <button v-on:click="goHome" data-dismiss="modal" id="buttonsOfCancel" type="button"
                class="btn btn-primary">Cancel</button>
            </span>

          </div>
        </div>
      </div>
    </div>
    <!-- logout modal -->

  </header>
</template>

<script>



export default {
  // test

  methods: {
    logout() {
      this.$store.commit("LOGOUT");
      this.$router.push("/dens");
    },
    goHome() {
      this.$router.push("/dens")
    },
// test

  

}}
</script>

<style scoped>
/* Apply a professional font  */
@import url('https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap');

header {
  display: flex;
  justify-content: space-between;
  font-family: 'Segoe UI';
  align-items: center;

  font-size: large;
  background: linear-gradient(90deg, rgba(252, 202, 70, 1) 20%, rgba(201, 197, 103, 1) 40%, rgba(97, 155, 138, 1) 80%);
}

#logoutModal {

  margin: auto;
  background-color: rgba(0, 0, 0, 0.1);
  width: 100%;
  margin: auto;

}

#logoutModalContent {
  justify-content: center;

  margin: auto;
  max-width: 50%;


}

#logoutHeader {
  border-bottom: black;
  justify-content: center;
}

#logoutModalBody {
  display: flex;
  justify-content: space-around;
}

#homeLink {
  display: flex;
  align-items: center;
  height: 8%;
}

#homeLink:hover {
  background-color: rgba(255, 255, 255, 0.15);
  border-radius: 10%;
}

img {
  height: auto;
  max-height: 65px;
}

#headerText {
  font-family: 'Segoe UI';
  font-weight: 700;
  color: rgba(252, 202, 70, 1);
  font-size: 1.5rem;
}

#favLog {
  display: flex;
  align-items: center;
}

.auth-link {
  color: rgba(252, 202, 70, 1);
  text-decoration: none;
  margin-left: 1rem;
  padding: 0.5rem 1rem;
  border-radius: 4px;
  transition: background-color 0.3s, color 0.3s;
}

.auth-link:hover {
  background-color: rgba(255, 255, 255, 0.1);
  color: #d3d3d3;
}

.auth-link:active {
  background-color: rgba(255, 255, 255, 0.2);
}

#LoginLink {
  color: white;
  
}
#buttonsOfLogout{
background-color: rgba(252, 202, 70, 1);
border-color:  rgba(252, 202, 70, 1);

}
#buttonsOfLogout:hover{
background-color: rgb(188, 152, 53);
border-color: rgba(252, 202, 70, 1);


}

#buttonsOfCancel{
  background-color: rgba(97, 155, 138, 1);
  border-color: rgba(97, 155, 138, 1);
}
#buttonsOfCancel:hover{
  background-color: rgb(75, 127, 111);
  border-color: rgba(97, 155, 138, 1);
}



</style>