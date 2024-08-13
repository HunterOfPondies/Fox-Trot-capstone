import axios from 'axios';

export default {


  getDens() {
     return axios.get('/dens' );
  },


  addDen(den) {
    return axios.post('/dens', den);
 },

 getCategories(){
  return axios.get('/categories' );

 },

 getFavorites(){
  return axios.get(`/${this.$store.state.user.username}/favorites`);

 },

 delete(den){
  return axios.delete(`/${den.denName}`, den)
}

}