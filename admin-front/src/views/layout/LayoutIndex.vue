<template>
  <div :class="classObj" class="app-wrapper">
    <div v-if="device==='mobile'&&sidebar.opened" class="drawer-bg" @click="handleClickOutside"/>
    <HeaderMain/>
    <div class="main-wrapper">
      <SidebarIndex class="sidebar-container"/>
      <div class="main-container">
        <NavbarIndex/>
        <app-main/>
      </div>
    </div>
  </div>
</template>

<script>
import { HeaderMain, SidebarIndex, NavbarIndex, AppMain } from './components'
import ResizeMixin from './mixin/ResizeHandler'

export default {
  name: 'LayoutFirst',
  components: {
    HeaderMain,
    SidebarIndex,
    NavbarIndex,
    AppMain
  },
  mixins: [ResizeMixin],
  computed: {
    sidebar() {
      return this.$store.state.app.sidebar
    },
    device() {
      return this.$store.state.app.device
    },
    classObj() {
      return {
        hideSidebar: !this.sidebar.opened,
        openSidebar: this.sidebar.opened,
        withoutAnimation: this.sidebar.withoutAnimation,
        mobile: this.device === 'mobile'
      }
    }
  },
  methods: {
    handleClickOutside() {
      this.$store.dispatch('CloseSideBar', { withoutAnimation: false })
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  @import "src/styles/layout.scss";
</style>
