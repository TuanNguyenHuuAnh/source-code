// db.js
import Dexie from 'dexie';

export const db = new Dexie('DCWDB');
db.version(1).stores({
  claims: 'key, value', // Primary key and indexed props
});