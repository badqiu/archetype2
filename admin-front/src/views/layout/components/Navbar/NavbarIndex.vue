<template>
  <div class="navbar">
    <hamburger :toggle-click="toggleSideBar" :is-active="sidebar.opened" class="hamburger-container"/>
    <BreadcrumbIndex class="breadcrumb-container"/>
    <!-- <div class="right-menu"></div> -->
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import BreadcrumbIndex from '@/components/Breadcrumb/BreadcrumbIndex'
import Hamburger from '@/components/Hamburger'
// import ThemePicker from '@/components/ThemePicker'

export default {
  components: {
    BreadcrumbIndex,
    Hamburger
  },
  data() {
    return {
      listLoading: true
    }
  },
  computed: {
    ...mapGetters([
      'sidebar',
      'avatar'
    ])
    // theme() {
    //   return this.$store.state.settings.theme
    // }
  },
  methods: {
    // themeChange(val) {
    //   this.$store.dispatch('settings/changeSetting', {
    //     key: 'theme',
    //     value: val
    //   })
    // },
    toggleSideBar() {
      this.$store.dispatch('ToggleSideBar')
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.navbar {
  height: 50px;
  line-height: 50px;
  border-bottom:1px solid #f5f7fa;
  .hamburger-container {
    line-height: 58px;
    height: 50px;
    float: left;
    padding: 0 10px;
  }
  .breadcrumb-container{
    float: left;
  }
  .screenfull {
    position: absolute;
    right: 90px;
    top: 16px;
    color: red;
  }
  .avatar-container {
    height: 50px;
    display: inline-block;
    position: absolute;
    right: 35px;
    .avatar-wrapper {
      cursor: pointer;
      margin-top: 5px;
      position: relative;
      line-height: initial;
      .user-avatar {
        width: 40px;
        height: 40px;
        border-radius: 10px;
      }
      .el-icon-caret-bottom {
        position: absolute;
        right: -20px;
        top: 25px;
        font-size: 12px;
      }
    }
  }
  .right-menu{
    float:right;
    padding-right:20px;
    padding-top:10px;
  }
  .videoTutorial{
    cursor: pointer;
    color: #333;
    font-size: 14px;
    &:hover{
      color:#409EFF;
    }
    i{
      margin-right:3px;
      font-size:16px;
    }
  }
}
.el-dialog__body{
  padding:20px;
}
</style>

