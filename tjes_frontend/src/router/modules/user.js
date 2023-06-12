import Layout from '@/layout'

export default [
  {
    path: '/user/base',
    component: Layout,
    redirect: '/user/base/info',
    meta: {
      title: '基本信息'
    },
    children: [
      {
        path: 'info',
        name: 'user_info',
        component: () => import('@/views/user/info/user_info.vue'),
        meta: {
          title: '个人信息',
        }
      },
      {
        path: 'reset_pwd',
        name: 'user_reset_pwd',
        component: () => import('@/views/user/info/user_reset_pwd.vue'),
        meta: {
          title: '密码修改',
        }
      },

    ]
  },
  {
    path: '/user/asset',
    component: Layout,
    redirect: '/user/asset/room',
    meta: {
      title: '资产管理'
    },
    children: [
      {
        path: 'room',
        name: 'user_room',
        component: () => import('@/views/user/asset/user_room.vue'),
        meta: {
          title: '房间管理',
        }
      },
      {
        path: 'carport',
        name: 'user_carport',
        component: () => import('@/views/user/asset/user_carport.vue'),
        meta: {
          title: '车位管理',
        }
      },

    ]
  },
  {
    path: '/user/',
    component: Layout,
    redirect: '/user/pay',
    meta: {
      title: '物业收费'
    },
    children: [
      {
        path: 'pay_record',
        name: 'user_pay',
        component: () => import('@/views/user/pay/user_pay.vue'),
        meta: {
          title: '缴纳费用',
        }
      },

    ]
  },
  {
    path: '/user/repair',
    component: Layout,
    redirect: '/user/repair/add',
    meta: {
      title: '报修管理'
    },
    children: [
      {
        path: 'add',
        name: 'rq_repair',
        component: () => import('@/views/admin/rq/guarantee/rq_repair_add.vue'),
        meta: {
          title: '申请报修',
        }
      },
    ]
  },
  {
    path: '/user/complaint',
    component: Layout,
    redirect: '/user/complaint/add',
    meta: {
      title: '投诉管理'
    },
    children: [
      {
        path: 'add',
        name: 'rq_complaint',
        component: () => import('@/views/admin/rq/guarantee/rq_complaint_add.vue'),
        meta: {
          title: '发起投诉',
        }
      },
    ]
  },
  {
    path: '/help-manager',
    component: Layout,
    redirect: '/help-manager',
    meta: {
      title: '辅助功能'
    },
    children: [
      {
        path: 'chat-bot',
        name: 'chat_bot',
        component: () => import('@/views/user/bot/chat_bot.vue'),
        meta: {
          title: '聊天Bot',
        }
      },
      {
        path: 'chatroom',
        name: 'chatroom',
        component: () => import('@/views/user/chat/multichat.vue'),
        meta: {
          title: '聊天室',
        }
      }
    ]
  },
]
