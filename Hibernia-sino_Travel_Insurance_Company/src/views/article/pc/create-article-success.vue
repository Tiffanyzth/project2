<template>
    <Card class="card">
        <div class="cotent">
            <Icon type="md-checkmark-circle" size="40" color="#19be6b"/>
            <h3>{{ $t("message.SendSuccess") }}</h3>
            <router-link :to="('/article/user')">
              <h4 class="link-text">{{ $t("message.ReturnMy") }}</h4>
            </router-link>
        </div>
    </Card>
</template>
<script>
export default {
  data() {
    return {
      
    };
  },
  mounted() {
    
  },
  methods: {
    
  }
};
</script>
<style>
.card {
	height: 400px;
}
.cotent{
	text-align:center;
	margin-top: 150px;
}
.cotent .link-text {
  color: rebeccapurple;
  margin-top: 20px;
}
</style>
