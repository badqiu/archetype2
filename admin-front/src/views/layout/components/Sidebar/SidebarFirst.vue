<template>
  <el-scrollbar v-loading="listLoading" wrap-class="scrollbar-wrapper">
    <el-menu :show-timeout="200" :default-active="$route.path" :router="true" :collapse="isCollapse" :collapse-transition="false" class="el-menu-vertical-demo">
      <el-menu-item v-for="(item, route) in projects" :key="route.path" :item="route" :route="'/first/'+item.projectId" :base-path="route.path" :index="'/'+item.projectId" :class="{'is-active':item.selected}" @click="selectProject(item)">
        <i class="el-icon-document"/>
        <span slot="title">{{ item.projectName }}</span>
      </el-menu-item>

    </el-menu>
  </el-scrollbar>
</template>

<style rel="stylesheet/scss" lang="scss" scoped>
  .scrollbar-wrapper {
    background:#f5f7fa;
    .el-menu{
      background: none;
    }
  }
</style>

<script>
import { mapGetters } from 'vuex'
export default {
  data() {
    return {
      loading: true,
      groups: [],
      formLabelWidth: '30%',
      listLoading: true,
      projects: [
        {
          projectId: 1,
          parentProjectId: -1,
          projectName: '开发组',
          projectCode: 'dev',
          remarks: 111,
          createTime: '2018-08-17 11:44:47',
          updateTime: '2018-08-17 15:32:21'
        },
        {
          projectId: 3,
          parentProjectId: -1,
          projectName: '分析组',
          projectCode: 'analysis',
          remarks: 333,
          createTime: '2018-08-17 15:34:43',
          updateTime: '2018-08-17 15:32:21'
        }
      ]
    }
  },
  computed: {
    ...mapGetters([
      'sidebar'
    ]),
    isCollapse() {
      return !this.sidebar.opened
    }
  },
  created() {
  },
  mounted() {
    this.SidebarItemList()
  },
  methods: {
    SidebarItemList() {
      this.listLoading = true
      var href = location.pathname
      var curProjectId = href.substring(href.lastIndexOf('/') + 1)
      for (var i = 0; i < this.projects.length; i++) {
        var project = this.projects[i]
        if (project.projectId === curProjectId) {
          project.selected = true
          this.$router.push({
            query: {
              projectName: project.projectName
            }
          })
        }
      }
      this.listLoading = false
    },
    selectProject(curProject) {
      this.$router.push({
        query: {
          projectCode: curProject.projectCode,
          projectName: curProject.projectName
        }
      })
      for (var i = 0; i < this.projects.length; i++) {
        var project = this.projects[i]
        this.projectCode = project.projectCode
        this.projectName = project.projectName
        if (project.projectId === curProject.ProjectId) {
          project.selected = true
        } else {
          project.selected = false
        }
      }
    }
  }
}
</script>
