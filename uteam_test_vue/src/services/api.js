import axios from 'axios'
import md5 from 'md5'

const publicKey = process.env.VUE_APP_MARVEL_PUBLIC_KEY
const privateKey = process.env.VUE_APP_MARVEL_PRIVATE_KEY
const ts = new Date().getTime()
const hash = md5(ts + privateKey + publicKey)

export const api = axios.create({
  baseURL: 'https://gateway.marvel.com/v1/public',
  params: {
    ts,
    apikey: publicKey,
    hash,
  },
})

export const getCharacters = () => api.get('/characters')
