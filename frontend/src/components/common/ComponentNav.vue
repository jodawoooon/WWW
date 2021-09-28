<template>
  <div>
    <nav id="nav">
      <span style="float: left; line-height: 57px; padding-left: 30px">
        <i class="el-icon-back"></i>
      </span>
      <span id="title">{{ $props.message }}</span>
      <el-dropdown style="float: right; line-height: 57px; padding-right: 30px">
        <span class="el-dropdown-link">
          <i class="el-icon-more"></i>
        </span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item>로그인</el-dropdown-item>
          <el-dropdown-item>로그아웃</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </nav>
  </div>
</template>

<script>
export default {
  name: "ComponentNav",
  props: ["message"],
};
</script>

<style scoped>
.el-dropdown-link {
  cursor: pointer;
}
#component-nav {
  height: 50px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 15px;
  width: 100%;
  color: #183a1d;
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  margin: 0 0 0 0;
  z-index: 3;
  background: white;
  border-bottom: 1px solid #e4e6e9;
}
.el-icon-arrow-down {
  font-size: 12px;
}
#nav {
  background-color: white;
  top: 0;
  padding-top: 5px;
  padding-left: 1%;
  padding-right: 1%;
  position: fixed;

  width: 100%;

  max-width: 425px;
  /*width: 100vw;*/
  height: 57px;
  line-height: 57px;
}

#title {
  font-weight: 700;
}
.material-icons.color292929 {
  color: #183a1d;
}
#component-nav a {
  text-align: start;
}
</style>
