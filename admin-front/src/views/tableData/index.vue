<template>
  <div class="app-container">
    <div class="filter-container">
      projectId: {{ projectId }} <br>
      projectCode: {{ projectCode }} <br>
      projectName: {{ projectName }}
    </div>
  </div>
</template>

<script>
// import $ from 'jquery'

export default {
  components: { },
  filters: {
  },
  data() {
    return {
      projectId: '',
      projectCode: '',
      projectName: ''
    }
  },
  watch: {
    '$route.params.projectId': function(val, oldVal) {
      if (val) {
        this.projectId = val
      }
    },
    '$route.query.projectCode': function(val, oldVal) {
      if (val) {
        this.projectCode = val
      }
    },
    '$route.query.projectName': function(val, oldVal) {
      if (val) {
        this.projectName = val
      }
    }
  },
  created() {
  },
  mounted() {

  },
  methods: {

  }

}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.custom-tree-node{
  padding-left:20px;
  position: relative;
  i{
    width: 16px;
    height: 16px;
    display: inline-block;
    position: absolute;
    top:0;
    left:0;
  }
}
</style>
