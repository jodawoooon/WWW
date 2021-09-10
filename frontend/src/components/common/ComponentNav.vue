<template>
    <div id="component-nav">
      <a href="#" @click.prevent="$emit('on-arrow')">
        <i class="material-icons color292929">arrow_back</i>
      </a>
        <div>
            {{ title }}
        </div>
    </div>
</template>

<script>

export default {
    name: "ComponentNav",
    props: {
      title: {
        type: String,
        default: '',
      }
    }
}
</script>

<style scoped>
#component-nav {
  height: 50px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 15px;
  width: 100%;
  color: #183a1d;
  position: fixed;
  top: 60px;
  left: 0;
  right: 0;
  margin: 0 auto;
  z-index: 3;
  background: white;
  border-bottom: 1px solid #e4e6e9;
}

  @media screen and (min-width: 425px) {
    #component-nav {
      width: 425px;
    }
}


.material-icons.color292929 {
  color: #183a1d;
}
#component-nav a {
  text-align: start;
   width: 60px;
}
#component-nav a {
  text-align: start;

}

#component-nav div{
  text-align : center;
}

</style>
