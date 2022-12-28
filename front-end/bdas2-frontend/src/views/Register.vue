<template>
  <div>
    <h2>Registrace</h2>
    <form @submit.prevent="handleSubmit">
      <div v-if="error" class="alert alert-danger">{{error}}</div>
      <div class="form-group">
        <label for="username">Login</label>
        <input type="text" v-model="user.username" name="username" class="form-control" :class="{ 'is-invalid': submitted && !user.username }" />
        <div v-show="submitted && !user.username" class="invalid-feedback">Login je povinný</div>
      </div>
      <div class="form-group">
        <label for="password">Heslo</label>
        <input type="password" v-model="user.password" name="password" class="form-control" :class="{ 'is-invalid': submitted && !user.password }" />
        <div v-show="submitted && !user.password" class="invalid-feedback">Heslo je povinné</div>
      </div>
      <div class="form-group">
        <label for="repeated-password">Heslo znovu</label>
        <input type="password" v-model="user.repeatedPassword" name="repeated-password" class="form-control"
               :class="{ 'is-invalid': submitted && (!user.repeatedPassword || user.password !== user.repeatedPassword) }"/>
        <div v-show="submitted && user.password !== user.repeatedPassword" class="invalid-feedback">Hesla se neshodují</div>
      </div>
      <div class="form-group">
        <button class="btn btn-primary" :disabled="loading">Potvrdit</button>
        <img v-show="loading" src="data:image/gif;base64,R0lGODlhEAAQAPIAAP///wAAAMLCwkJCQgAAAGJiYoKCgpKSkiH/C05FVFNDQVBFMi4wAwEAAAAh/hpDcmVhdGVkIHdpdGggYWpheGxvYWQuaW5mbwAh+QQJCgAAACwAAAAAEAAQAAADMwi63P4wyklrE2MIOggZnAdOmGYJRbExwroUmcG2LmDEwnHQLVsYOd2mBzkYDAdKa+dIAAAh+QQJCgAAACwAAAAAEAAQAAADNAi63P5OjCEgG4QMu7DmikRxQlFUYDEZIGBMRVsaqHwctXXf7WEYB4Ag1xjihkMZsiUkKhIAIfkECQoAAAAsAAAAABAAEAAAAzYIujIjK8pByJDMlFYvBoVjHA70GU7xSUJhmKtwHPAKzLO9HMaoKwJZ7Rf8AYPDDzKpZBqfvwQAIfkECQoAAAAsAAAAABAAEAAAAzMIumIlK8oyhpHsnFZfhYumCYUhDAQxRIdhHBGqRoKw0R8DYlJd8z0fMDgsGo/IpHI5TAAAIfkECQoAAAAsAAAAABAAEAAAAzIIunInK0rnZBTwGPNMgQwmdsNgXGJUlIWEuR5oWUIpz8pAEAMe6TwfwyYsGo/IpFKSAAAh+QQJCgAAACwAAAAAEAAQAAADMwi6IMKQORfjdOe82p4wGccc4CEuQradylesojEMBgsUc2G7sDX3lQGBMLAJibufbSlKAAAh+QQJCgAAACwAAAAAEAAQAAADMgi63P7wCRHZnFVdmgHu2nFwlWCI3WGc3TSWhUFGxTAUkGCbtgENBMJAEJsxgMLWzpEAACH5BAkKAAAALAAAAAAQABAAAAMyCLrc/jDKSatlQtScKdceCAjDII7HcQ4EMTCpyrCuUBjCYRgHVtqlAiB1YhiCnlsRkAAAOwAAAAAAAAAAAA==" />
      </div>
      <p class="forgot-password text-right">
        Již registrován
        <router-link :to="{name: 'Login'}">přihlásit?</router-link>
      </p>
    </form>
  </div>
</template>

<script>

export default {
  name: "Register.vue",
  data () {
    return {
      user: {
        username: '',
        password: '',
        repeatedPassword: ''
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
      if (!(user.username && user.password) || (user.password !== user.repeatedPassword)) {
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