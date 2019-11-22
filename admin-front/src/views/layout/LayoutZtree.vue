<template>
  <div :class="classObj" class="app-wrapper">
    <div v-if="device==='mobile'&&sidebar.opened" class="drawer-bg" @click="handleClickOutside"/>
    <HeaderMain/>
    <div class="main-wrapper">
      <SidebarZtree class="sidebar-container"/>
      <div class="main-container">
        <NavbarZtree/>
        <app-main/>
      </div>
    </div>
  </div>
</template>

<script>
import { HeaderMain, SidebarZtree, NavbarZtree, AppMain } from './components'
import ResizeMixin from './mixin/ResizeHandler'

export default {
  name: 'LayoutZtree',
  components: {
    HeaderMain,
    SidebarZtree,
    NavbarZtree,
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
