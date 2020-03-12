// В thymeleaf  как javascript получили переменную  frontEndData profile - ато-ный пользователь messges список-->
function getIndex(list, id){
    for (let i = 0; i<list.length; i++){
        if (list[i].id ===id){
            return i;
        }
    }
    return  -1;
}
// ресур отвечает за связь с  контролером
let mes_res = Vue.resource('/messages/{id}');
//компонент кнопок для редактированияи
Vue.component('edit_component',{
    props: {
        'messages': Array,
        'edited_mes':Object,
        'editedMes':Object


    },
    data:
        function(){
            return {
                text:'',
                id:'',
                dateCreate:'',
                enabled:false,
                focused:false,
                error_mes:''
            }
        }
    ,
    watch :{
        editedMes:function (newVal, oldVal) {
            alert("edited " +newVal);
            this.id = newVal.id;
            this.text = newVal.text;
            this.dateCreate = newVal.dateCreate;
            this.enabled=true;
            this.focused = true;
        }

    },
    // computed:{
    //     editedMes: {
    //             // геттер:
    //             get: function () {
    //                 return {'id':this.id, 'text':this.text,'dateCreate': this.dateCreate}
    //             },
    //             // сеттер:
    //             set: function (newValue) {
    //                 this.id = newValue.id;
    //                 this.text = newValue.text;
    //                 this.dateCreate = newValue.dateCreate;
    //             }
    //     }
    // },
    methods:{
      addMes:function () {
        this.text = '';
        this.id = '';
        //Elementbyid('form_text').focus();
          this.enabled=true;
          this.focused = true;
      },
      finish:function(){
          this.enabled =false;
          this.text = '';
          this.id = '';
      } ,
        save:function (){

          this.enabled =false;
            if (this.id===''){
                let mess = {'text':this.text};
                mes_res.save({}, mess).then(result =>{
                    result.json().then(
                        data=>{

                            this.messages.push(data);
                        }
                    )
                } )
            }else{
                let  mes = {'id':this.id, 'text':this.text, 'dateCreate':this.dateCreate};
                mes_res.update({id:this.id},mes).then(result=>{
                    result.json().then(data=>{
                        console.log(data);
                        let index = getIndex(this.messages, data.id);
                        if (index>-1){
                            this.messages.splice(index,1,data);
                        }else{
                            this.messages.push(data);
                        }
                    })
                },
                    error=>{
                        this.error_mes ='Error saved object! ' + error.text ;
                    })
            }
            this.finish();
        }
    },
    template:'<div><input type ="text" name = "text" v-model = "text" id="form_text" :disabled = "!enabled"  @focus="focused = true"/>' +
        '<button @click = "addMes" :disabled = "enabled" >add</button><button @click = "save" :disabled = "!enabled" >save</button>' +
        '<button @click = "finish" :disabled = "!enabled">Cansel</button> </div>'
});

//компонент строк с цыклом
Vue.component('data-table', {
    props:{
        'messages':Array,

    },
    data:
        function() {
            return {
                count: 0,

                resultMes:'',
                edited_mes:null
            }
        },
    computed: {
        editedMes:{
            get:function(){
                return this.edited_mes;
            },
            set:function (newVal) {
             this.edited_mes = newVal;
            }
        }
    },
    methods:{
      deleteRow:function(message){

          mes_res.remove({id:message.id}).then(result =>{
            if (result.ok){
                let index = getIndex(this.messages, message.id);
                this.messages.splice(index,1);
                this.resultMes = 'Success deleted';
            }
          },
              error =>{
                  this.resultMes = 'Error deleted ' +error;
          });
      },
      editRow:function (message) {
        this.editedMes = message;
      }
    },
    //template : '<ul><li v-for="mess in messages">{{mess.id}} {{mess.text}} {{mess.dateCreate}}</li> </ul>'
      template: '<div><edit_component v-bind:messages="messages"  :editedMes = "edited_mes"/>' +
          '<ul><data-row v-for="mess in messages" v-bind:mess = "mess" :deleteRow = "deleteRow"  v-bind:editRow = "editRow" /></ul> ' +
          '<p>{{resultMes}}</p></div>',

});
//компонент строки
Vue.component('data-row',{
    props:[
        'mess',
        'deleteRow',
        'editRow'
    ],
    data:
        function() {
            return {
                count: 0
            }
        }
    ,
    methods: {
      deleteItem:function () {
        this.deleteRow(this.mess);
      },
       editItem:function () {
            this.editRow(this.mess);
       }
    },
    template:'<li><span class="ident">{{mess.id}}</span><span> -  </span><span class="">{{mess.text}}</span> <span> -  </span> <span class="dateCreate">{{mess.dateCreate}}' +
        '</span><button @click = "editItem">edit</button><button @click ="deleteItem" >delete</button></li>'

});

//Основной компонент
let app = new Vue({
    el: '#app',
    data: {
        test: ' Vue!',
        profile: null,
        messages : []
    },
    created:function(){
        // mes_res.get().then(result =>{
        //     console.log(result.json());
        //     result.json().then(
        //         data => {data.forEach(mess => this.messages.push(mess) )}
        //     )});
        if  (frontEndData.profile  == null){
            this.profile = null
        }else{
            this.profile = frontEndData.profile;
        }
        if (frontEndData.messages !=null){
            this.messages = frontEndData.messages;
        }



    },
    template :  '<div>' +
        '<h1>{{test}} </h1> '+

        ' <div v-if = "!profile"> Авторизируйтесь <a href="/login">Авторизироваться</a></div>' +
        '<div v-else>' +
        '<h2>Привет {{profile.username}} <form action="/logout" method="post"  > <input type="submit" value="Sign Out"/>' +
        '</form></h2>' +
        '<data-table v-bind:messages="messages"/></div></div>'

});