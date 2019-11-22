<template>
  <div>
    <el-header class="app-header">
      <div class="logo"><router-link class="inlineBlock" to="/"><img src="../../../../static/images/logo2019.png"></router-link></div>
      <headernav class="headernav"/>
      <div class="avatar-container">
        <el-dropdown trigger="click" @command="logout">
          <div class="avatar-wrapper">
            <svg-icon icon-class="user" />
            <span>{{ projectName }}</span>
            <i class="el-icon-caret-bottom"/>
            <!-- <img class="user-avatar" src="../../../../static/images/default.png"> -->
          </div>
          <el-dropdown-menu slot="dropdown" class="user-dropdown">
            <el-dropdown-item><a href="https://dcauth.xoyo.com/editUser.do" target="_blank">个人信息修改</a></el-dropdown-item>
            <el-dropdown-item><a href="https://dcauth.xoyo.com/index.do" target="_blank">权限申请</a></el-dropdown-item>
            <!-- <el-dropdown-item><a href="/logout.do">退出系统</a></el-dropdown-item> -->
          </el-dropdown-menu>
        </el-dropdown>
        <el-menu class="side-menu-right">
          <el-menu-item class="ivu-menu-item" index="">
            <a href="/logout.do"><i><svg-icon icon-class="out" /></i><span slot="title">退出</span></a>
          </el-menu-item>
        </el-menu>
      </div>
    </el-header>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import Breadcrumb from '@/components/Breadcrumb'
import Hamburger from '@/components/Hamburger'
import Headernav from '@/components/Headernav'
import {
  removeToken,
  removePassport,
  removeSsoValidTime
} from '@/utils/auth.js'

export default {
  components: {
    Breadcrumb,
    Hamburger,
    Headernav
  },
  data() {
    return {
      projectName: ''
    }
  },
  computed: {
    ...mapGetters([
      'sidebar',
      'avatar'
    ])
  },
  mounted() {
  },
  methods: {
    toggleSideBar() {
      this.$store.dispatch('ToggleSideBar')
    },
    logout(command) {
      removeToken()
      removePassport()
      removeSsoValidTime()
      location.href = 'https://dcauth.xoyo.com/logout.do?redirectURL=' + location.href
    }
  }
}

</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.app-header {
    position: fixed;
    box-sizing: border-box;
    width: 100%;
    height:50px !important;
    font-size: 14px;
    line-height: 50px;
    background-image: -webkit-linear-gradient(#54b4eb, #2fa4e7 60%, #1d9ce5);
    background-image: -o-linear-gradient(#54b4eb, #2fa4e7 60%, #1d9ce5);
    background-image: -webkit-gradient(linear, left top, left bottom, from(#54b4eb), color-stop(60%, #2fa4e7), to(#1d9ce5));
    background-image: linear-gradient(#54b4eb, #2fa4e7 60%, #1d9ce5);
    background-repeat: no-repeat;
    filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#ff54b4eb', endColorstr='#ff1d9ce5', GradientType=0);
    border-bottom: 1px solid #178acc;
    -webkit-filter: none;
    filter: none;
    -webkit-box-shadow: 0 1px 10px rgba(0,0,0,0.1);
    box-shadow: 0 1px 10px rgba(0,0,0,0.1);
    z-index:1000;
    padding:0 10px 0 15px;
    overflow:hidden;
    .logo{
        float: left;
        cursor:pointer;
        img{
            width:100%;
        }
    }
    .hamburger-container {
        line-height: 50px;
        height: 50px;
        float: left;
        padding: 0 10px;
        color:#fff;
    }
    .headernav{
        float:left;
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
        right: 0;
        .el-dropdown{
          padding:0 20px;
        }
        .avatar-wrapper {
            cursor: pointer;
            position: relative;
            color:#fff;
            .user-avatar {
                width: 40px;
                height: 40px;
                border-radius: 10px;
            }
            .el-icon-caret-bottom {
                position: absolute;
                right: -15px;
                top: 20px;
                font-size: 12px;
                color:#fff;
            }
        }
    }
    .side-menu-right{
      float: right;
      background: none;
      border-right:none;
      .ivu-menu-item{
        color: #fff;
        padding: 0 20px;
        display: inline-block;
        font-size: 14px;
        border-bottom: none;
        margin-right: 0px;
        height: 50px;
        line-height: 49px;
        &:hover{
          background: #178acc;
          color: #fff;
        }
        i{
          margin-right:3px;
          font-size:18px;
        }
      }
    }
}
.el-dropdown-menu__item{
  &::before{
    margin-right:5px;
  }
}
.el-popper{
  margin-top:0 !important;
}
.el-menu-item [class^=el-icon-]{
  width:auto !important;
  font-size: 14px !important;
}
</style>
