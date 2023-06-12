import Layout from '@/layout'

export default [
  {
    path: '/rq-manager',
    component: Layout,
    redirect: '/rq-manager/info',
    meta: {
      title: '小区管理'
    },
    children: [
      {
        path: 'info',
        name: 'rq_info',
        component: () => import('@/views/admin/rq/info/rq_info.vue'),
        meta: {
          title: '基本信息管理',
        }
      },
      {
        path: 'facilities',
        name: 'rq_facilities',
        component: () => import('@/views/admin/rq/info/rq_facilities.vue'),
        meta: {
          title: '周边设施管理',
        }
      },
      {
        path: 'notices',
        name: 'rq_notices',
        component: () => import('@/views/admin/rq/info/rq_notices.vue'),
        meta: {
          title: '物业公告管理',
        }
      },
    ]
  },
  {
    path: '/building-manager',
    component: Layout,
    redirect: '/building-manager/building',
    meta: {
      title: '楼盘管理'
    },
    children: [
      {
        path: 'building',
        name: 'rq_building',
        component: () => import('@/views/admin/rq/asset/rq_building.vue'),
        meta: {
          title: '楼宇管理',
        }
      },
      {
        path: 'room',
        name: 'rq_room',
        component: () => import('@/views/admin/rq/asset/rq_room.vue'),
        meta: {
          title: '房间管理',
        }
      }
    ]
  },
  {
    path: '/park-manager',
    component: Layout,
    redirect: '/park-manager/carport',
    meta: {
      title: '车位管理'
    },
    children: [
      {
        path: 'carport',
        name: 'rq_carport',
        component: () => import('@/views/admin/rq/asset/rq_carport.vue'),
        meta: {
          title: '车位管理',
        }
      },
    ]
  },
  {
    path: '/guarantee-manager',
    component: Layout,
    redirect: '/guarantee-manager/repair',
    meta: {
      title: '生活管理'
    },
    children: [
      {
        path: 'repair',
        name: 'rq_repair',
        component: () => import('@/views/admin/rq/guarantee/rq_repair_manager.vue'),
        meta: {
          title: '报修管理',
        }
      },
      {
        path: 'complaint',
        name: 'rq_complaint',
        component: () => import('@/views/admin/rq/guarantee/rq_complaint_manager.vue'),
        meta: {
          title: '投诉管理',
        }
      },
    ]
  },
  {
    path: '/charge-manager',
    component: Layout,
    redirect: '/charge-manager/type',
    meta: {
      title: '收费管理'
    },
    children: [
      {
        path: 'type',
        name: 'rq_charge_type',
        component: () => import('@/views/admin/rq/charge/rq_charge_type.vue'),
        meta: {
          title: '收费类型管理',
        }
      },
    ]
  },
  {
    path: '/user-manager',
    component: Layout,
    redirect: '/user-manager/estate_user',
    meta: {
      title: '用户管理'
    },
    children: [
      {
        path: 'estate_user',
        name: 'estate_user',
        component: () => import('@/views/admin/rq/manager/user_manager.vue'),
        meta: {
          title: '账号信息管理',
        }
      },
      {
        path: 'household',
        name: 'user_household',
        component: () => import('@/views/user/asset/user_household.vue'),
        meta: {
          title: '住户信息管理',
        }
      },
    ]
  },
]
