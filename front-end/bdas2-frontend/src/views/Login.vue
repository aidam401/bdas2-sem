<template>
  <div class="main-wrapper">
    <MainHeader title="Přihlášení"/>
    <form @submit.prevent="handleSubmit">
      <div v-if="error" class="alert alert-danger">{{error}}</div>
      <div class="form-group">
        <label for="login">Login</label>
        <input type="text" v-model="user.LOGIN" name="login" class="form-control" :class="{ 'is-invalid': submitted && !user.login }" />
        <div v-show="submitted && !user.LOGIN" class="invalid-feedback">Login je povinný</div>
      </div>
      <div class="form-group">
        <label for="heslo">Heslo</label>
        <input type="password" v-model="user.HESLO" name="heslo" class="form-control" :class="{ 'is-invalid': submitted && !user.heslo }" />
        <div v-show="submitted && !user.HESLO" class="invalid-feedback">Heslo je povinné</div>
      </div>
      <div class="form-group">
        <button class="btn btn-primary" :disabled="loading">Přihlásit</button>
        <img v-show="loading" src="data:image/gif;base64,R0lGODlhEAAQAPIAAP///wAAAMLCwkJCQgAAAGJiYoKCgpKSkiH/C05FVFNDQVBFMi4wAwEAAAAh/hpDcmVhdGVkIHdpdGggYWpheGxvYWQuaW5mbwAh+QQJCgAAACwAAAAAEAAQAAADMwi63P4wyklrE2MIOggZnAdOmGYJRbExwroUmcG2LmDEwnHQLVsYOd2mBzkYDAdKa+dIAAAh+QQJCgAAACwAAAAAEAAQAAADNAi63P5OjCEgG4QMu7DmikRxQlFUYDEZIGBMRVsaqHwctXXf7WEYB4Ag1xjihkMZsiUkKhIAIfkECQoAAAAsAAAAABAAEAAAAzYIujIjK8pByJDMlFYvBoVjHA70GU7xSUJhmKtwHPAKzLO9HMaoKwJZ7Rf8AYPDDzKpZBqfvwQAIfkECQoAAAAsAAAAABAAEAAAAzMIumIlK8oyhpHsnFZfhYumCYUhDAQxRIdhHBGqRoKw0R8DYlJd8z0fMDgsGo/IpHI5TAAAIfkECQoAAAAsAAAAABAAEAAAAzIIunInK0rnZBTwGPNMgQwmdsNgXGJUlIWEuR5oWUIpz8pAEAMe6TwfwyYsGo/IpFKSAAAh+QQJCgAAACwAAAAAEAAQAAADMwi6IMKQORfjdOe82p4wGccc4CEuQradylesojEMBgsUc2G7sDX3lQGBMLAJibufbSlKAAAh+QQJCgAAACwAAAAAEAAQAAADMgi63P7wCRHZnFVdmgHu2nFwlWCI3WGc3TSWhUFGxTAUkGCbtgENBMJAEJsxgMLWzpEAACH5BAkKAAAALAAAAAAQABAAAAMyCLrc/jDKSatlQtScKdceCAjDII7HcQ4EMTCpyrCuUBjCYRgHVtqlAiB1YhiCnlsRkAAAOwAAAAAAAAAAAA==" />
      </div>
      <p class="forgot-password text-right">
        Chcete se <router-link :to="{name: 'Register'}">zaregistrovat?</router-link>
      </p>
    </form>
  </div>
</template>

<script>

import MainHeader from "@/components/MainHeader.vue";
export default {
  name: "Login.vue",
  components: {MainHeader},
  data () {
    return {
      user: {
        LOGIN: '',
        HESLO: ''
      },
      submitted: false,
      loading: false,
      returnUrl: '',
      error: ''
    }
  },
  created () {
    // reset login status
    this.$store.dispatch("auth/logout");
    // get return url from route parameters or default to '/'
    this.returnUrl = this.$route.query.returnUrl || '/';
  },
  methods: {
    handleSubmit (e) {
      this.submitted = true;

      const { user } = this;
      if (!(user.LOGIN && user.HESLO)) {
        return;
      }

      this.loading = true;
      this.$store.dispatch("auth/login", user).then(
          data => this.$router.push(this.returnUrl),
          error => {
            this.error = error;
            this.loading = false;
          }
      );
    }
  }
}
</script>