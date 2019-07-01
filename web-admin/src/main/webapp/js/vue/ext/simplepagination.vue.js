'use strict';

/**
 * 分页组件,paginator要有page,pageSize等属性
 */
Vue.component('simplepagination',{
	props:['paginator'],
	methods:{
		fireChangePage : function(page) {
			this.$emit('changepage',page);
		},
		fireChangePageSize : function() {
			this.$emit('changepagesize',this.paginator.pageSize);
		}
	},
	template: multiline(function() {
		/*!@preserve
		<div class="fixed-table-pagination" >
			
			<div class="pull-left pagination-detail">
				<span class="pagination-info">{{paginator.startRow}} - {{paginator.endRow}} of {{paginator.totalItems}}</span>
				<span class="page-list">
					<select v-model="paginator.pageSize" @change="fireChangePageSize" class="form-control" style="width:80px;">
						<option>10</option>
						<option>50</option>
						<option>100</option>
					</select>
				</span>
			</div>
			
			<div class="pull-right pagination">
			  <ul class="pagination ">
			  	
			  	<!-- firstPage -->
			  	<li v-bind:class="paginator.firstPage ? 'disabled' : ''">
			      <a aria-label="firstPage" href="#" @click="fireChangePage(1)">
			        <span aria-hidden="true">|&laquo;</span>
			      </a>
			    </li>
			    
			    <!-- prePage -->
			    <li v-bind:class="paginator.hasPrePage ? '' : 'disabled'">
			      <a aria-label="Previous" href="#" @click.prevent="fireChangePage(paginator.prePage)">
			        <span aria-hidden="true">&laquo;</span>
			      </a>
			    </li>
			    
			    <!-- page -->
		    	<li v-for="page in paginator.slider" v-bind:class="[ page == paginator.page ? 'active' : '']">
					<a href="#" @click.prevent="fireChangePage(page)">{{page}}</a>
		    	</li>
				
				<!-- nextPage -->
			    <li v-bind:class="paginator.hasNextPage ? '' : 'disabled'">
			      <a aria-label="Next" herf="#" @click.prevent="fireChangePage(paginator.nextPage);">
			        <span aria-hidden="true">&raquo;</span>
			      </a>
			    </li>
			    
			    <!-- lastPage -->
			    <li v-bind:class="paginator.lastPage ? 'disabled' : ''">
			      <a aria-label="lastPage" herf="#" @click="fireChangePage(paginator.totalPages);">
			        <span aria-hidden="true">&raquo;|</span>
			      </a>
			    </li>
				
			  </ul>
			<div>
			
		</div>
		*/})
});
