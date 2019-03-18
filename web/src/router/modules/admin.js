
import adminHome from '@/views/admin/home/index';

export default [
  {
    path: '/admin',
    name: 'adminHome',
    component: adminHome,
    meta: {
      requireLogin: true,
    },
  },
]
;
