Vue.component('submit-dialog', {
  props: ['id','title','submitText'],
  template: multiline(function() {
	  /*!@preserve
	  <div class='modal fade submit-dialog ' tabindex='-1' role='dialog' :id='id'>
		  <div class='modal-dialog' style='width:1200px;' >
		    <div class='modal-content' >
		    
		      <div class='modal-header text-center'>
		        <button type='button' class='close' data-dismiss='modal' aria-label='Close'><span aria-hidden='true'>&times;</span></button>
		        <h4 class='modal-title'>{{title}}</h4>
		      </div>
		      
		      <div class='modal-body' >
	      		<slot></slot>
		      </div>
		      
		      <div class='modal-footer'>
		      	<slot name="footer">
			      	<div class='text-center'>
			        	<button type='button' class='btn btn-primary' @click='fireSubmitEvent'>{{submitText}}</button>
			        	<button type='button' class='btn btn-default' data-dismiss='modal'>关闭</button>
			      	</div>
		      	</slot>
		      </div>
		      
		    </div>
		  </div>
		</div>
  */}),
  methods : {
	  fireSubmitEvent : function() {
		  this.$emit('submit');
	  }
  }
});

Vue.component('select2', {
	  props: ['config', 'value','id','name',"clazz"],
	  template: multiline(function() {
		  /*!@preserve
			<select :id="id" class="form-control select2" style="width:100%" >
	    		<slot></slot>
	  		</select>   
		  */}),
	  mounted: function () {
	    var vm = this
	    $(this.$el)
	      .val(this.value)
	      // init select2
	      .select2(this.config)
	      // emit event on change.
	      .on('change', function () {
	        vm.$emit('input', this.value)
	      })
	  },
	  watch: {
	    value: function (value) {
	      // update value
	      $(this.$el).val(value)
	    },
	    config: function (config) {
	      // update config
	      $(this.$el).select2(config)
	    }
	  },
	  destroyed: function () {
	    $(this.$el).off().select2('destroy')
	  }
	  
});