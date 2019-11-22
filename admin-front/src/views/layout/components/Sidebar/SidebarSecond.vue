<template>
  <el-row class="navbar">
    <el-col>
      <el-menu :router="true" :default-active="$route.path" class="el-menu-vertical-demo">
        <el-submenu v-for="(item, index) in groups" :key="index" :index="'/'+item.projectId">
          <template slot="title">
            <i class="el-icon-document"/>
            <span>{{ item.projectName }}</span>
          </template>
          <el-menu-item-group>
            <el-menu-item v-for="(childItem, childIndex) in getGroupProject(item.projectId)" :key="childIndex" :route="'/second/'+childItem.projectId+'?projectCode='+childItem.projectCode+'&projectName='+childItem.projectName" index="">
              {{ childItem.projectName }}
            </el-menu-item>
          </el-menu-item-group>
        </el-submenu>
      </el-menu>
    </el-col>
  </el-row>
</template>

<style rel="stylesheet/scss" lang="scss" scoped>
  .navbar {
    background-color: #fff;
    .el-menu {
      background: none;
    }
  }
</style>

<script>
export default {
  data() {
    return {
      loading: true,
      groups: [],
      formLabelWidth: '30%',
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
          projectId: 2,
          parentProjectId: -1,
          projectName: '分析组',
          projectCode: 'analysis',
          remarks: 333,
          createTime: '2018-08-17 15:34:43',
          updateTime: null
        },
        {
          projectId: 9,
          parentProjectId: 1,
          projectName: '测试项目',
          projectCode: 'test',
          remarks: '',
          createTime: '2018-08-28 10:35:58',
          updateTime: '2018-08-29 17:50:19'
        },
        {
          projectId: 10,
          parentProjectId: 2,
          projectName: '艾瑞指数',
          projectCode: 'iresearch',
          remarks: '',
          createTime: '2018-09-19 11:02:46',
          updateTime: '2018-10-23 10:05:14'
        }
      ]
    }
  },
  created() {
  },
  mounted() {
    this.init()
  },
  methods: {
    init() {
      this.groups = this.projects.filter(function(item, index, array) {
        return item.parentProjectId === -1
      })
    },
    getGroupProject(parentProjectId) {
      const groupProjects = this.projects.filter(function(item, index, array) {
        return item.parentProjectId === parentProjectId
      })
      return groupProjects
    }
  }
}
</script>
